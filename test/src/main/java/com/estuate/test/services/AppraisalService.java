package com.estuate.test.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.estuate.test.entities.Category;
import com.estuate.test.entities.Employee;
import com.estuate.test.repo.CategoryRepository;
import com.estuate.test.repo.EmployeeRepository;

@Service
@Component
public class AppraisalService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> getActualPercentages() {

		List<Employee> employees = employeeRepository.findAll();
		List<Category> categories = categoryRepository.findAll();

		Map<Character, Long> actualCounts = employees.stream()
				.collect(Collectors.groupingBy(Employee::getRating, Collectors.counting()));

		long totalEmployees = employees.size();

		for (Category category : categories) {

			long actualCount = actualCounts.getOrDefault(category.getCategory_Id(), 0L);
			double actualPercentage = totalEmployees > 0 ? (double) actualCount / totalEmployees * 100 : 0.0;

			category.setActual_Percentage(actualPercentage);

		}

		categoryRepository.saveAll(categories);

		return categoryRepository.findAll();
	}

	public List<CategoryDeviation> getDeviations() {
		List<Employee> employees = employeeRepository.findAll();
		List<Category> categories = categoryRepository.findAll();

		Map<Character, Long> actualCounts = employees.stream()
				.collect(Collectors.groupingBy(Employee::getRating, Collectors.counting()));

		System.out.print(actualCounts);

		long totalEmployees = employees.size();

		List<CategoryDeviation> deviations = new ArrayList<>();

		for (Category category : categories) {

			long actualCount = actualCounts.getOrDefault(category.getCategory_Id(), 0L);
			double actualPercentage = totalEmployees > 0 ? (double) actualCount / totalEmployees * 100 : 0.0;

			category.setActual_Percentage(actualPercentage);

			double deviation = actualPercentage - category.getStandard_Percentage();

			deviations.add(new CategoryDeviation(category.getCategory_Id(), actualPercentage,
					category.getStandard_Percentage(), deviation));
		}

		return deviations;
	}

	public List<EmployeeRevision> suggestRevisions(List<CategoryDeviation> deviations) {
		List<Employee> employees = employeeRepository.findAll();
		List<EmployeeRevision> revisedEmployees = new ArrayList<>();

		deviations.sort(Comparator.comparing(CategoryDeviation::getCategory));

		for (CategoryDeviation deviation : deviations) {
			char categoryId = deviation.getCategory();
			double deviationValue = deviation.getDeviation();

			if (deviationValue > 0) {

				List<CategoryDeviation> deficitCategories = deviations.stream().filter(d -> d.getDeviation() < 0)
						.sorted(Comparator.comparing(CategoryDeviation::getDeviation)).collect(Collectors.toList());

				for (CategoryDeviation deficitCategory : deficitCategories) {
					char deficitCategoryId = deficitCategory.getCategory();
					double deficitValue = -deficitCategory.getDeviation();

					List<Employee> surplusEmployees = employees.stream().filter(emp -> emp.getRating() == categoryId)
							.limit((long) (Math.min(deviationValue, deficitValue) / 100 * employees.size()))
							.collect(Collectors.toList());

					for (Employee emp : surplusEmployees) {
						char originalCategory = emp.getRating();
						char swappedCategory = deficitCategoryId;

						revisedEmployees.add(new EmployeeRevision(emp, "Must be swapped from category: "
								+ originalCategory + " to category: " + swappedCategory));

						deviationValue -= 100.0 / employees.size();
						deficitCategory.setDeviation(deficitCategory.getDeviation() + 100.0 / employees.size());

						if (deviationValue <= 0)
							break;
					}

					if (deviationValue <= 0)
						break;
				}
			} else if (deviationValue < 0) {

				List<CategoryDeviation> surplusCategories = deviations.stream().filter(d -> d.getDeviation() > 0)
						.sorted((d1, d2) -> Double.compare(d2.getDeviation(), d1.getDeviation()))
						.collect(Collectors.toList());

				for (CategoryDeviation surplusCategory : surplusCategories) {
					char surplusCategoryId = surplusCategory.getCategory();
					double surplusValue = surplusCategory.getDeviation();

					List<Employee> surplusEmployees = employees.stream()
							.filter(emp -> emp.getRating() == surplusCategoryId)
							.limit((long) (Math.min(-deviationValue, surplusValue) / 100 * employees.size()))
							.collect(Collectors.toList());

					for (Employee emp : surplusEmployees) {
						char originalCategory = emp.getRating();
						char swappedCategory = categoryId;

						revisedEmployees.add(new EmployeeRevision(emp, "Must be swapped from category: "
								+ originalCategory + " to category: " + swappedCategory));
						deviationValue += 100.0 / employees.size();
						surplusCategory.setDeviation(surplusCategory.getDeviation() - 100.0 / employees.size());

						if (deviationValue >= 0)
							break;
					}

					if (deviationValue >= 0)
						break;
				}
			}
		}

		return revisedEmployees;
	}

	public List<Employee> getAdjustedEmployeeRatings() {

		List<CategoryDeviation> deviations = getDeviations();
		List<Employee> employees = employeeRepository.findAll();

		Map<Character, Double> deviationMap = deviations.stream()
				.collect(Collectors.toMap(CategoryDeviation::getCategory, CategoryDeviation::getDeviation));

		List<Employee> adjustedEmployees = new ArrayList<>(employees);

		while (true) {

			Character overRepresented = getCategoryWithHighestDeviation(deviationMap, true);
			Character underRepresented = getCategoryWithHighestDeviation(deviationMap, false);

			if (overRepresented == null || underRepresented == null)
				break;

			Employee employeeToAdjust = adjustedEmployees.stream()
					.filter(emp -> emp.getRating().equals(overRepresented)).findFirst().orElse(null);

			if (employeeToAdjust != null) {

				employeeToAdjust.setRating(underRepresented);

				double totalEmployees = adjustedEmployees.size();
				deviationMap.put(overRepresented, deviationMap.get(overRepresented) - (100.0 / totalEmployees));
				deviationMap.put(underRepresented, deviationMap.get(underRepresented) + (100.0 / totalEmployees));
			}
		}

		return adjustedEmployees;
	}

	private Character getCategoryWithHighestDeviation(Map<Character, Double> deviations, boolean positive) {
		return deviations.entrySet().stream()
				.filter(entry -> (positive && entry.getValue() > 0) || (!positive && entry.getValue() < 0))
				.max(Comparator.comparingDouble(entry -> Math.abs(entry.getValue()))).map(Map.Entry::getKey)
				.orElse(null);
	}

}
