package com.estuate.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estuate.test.entities.Category;
import com.estuate.test.services.AppraisalService;
import com.estuate.test.services.CategoryDeviation;
import com.estuate.test.services.EmployeeRevision;

@RestController
@RequestMapping("/api/appraisal")
public class AppraisalController {

	@Autowired
	private AppraisalService appraisalService;

	@GetMapping("/actual-percentages")
	public List<Category> getActualPercentages() {
		return appraisalService.getActualPercentages();
	}

	@GetMapping("/deviations")
	public List<CategoryDeviation> getDeviations() {
		return appraisalService.getDeviations();
	}

	@GetMapping("/revisions")
	public List<EmployeeRevision> getRevisedRatings() {
		return appraisalService.suggestRevisions(appraisalService.getDeviations());
	}

}
