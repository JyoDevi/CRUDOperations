package com.practice.springboot;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

@Controller
public class MyController {
	@Autowired
	CustomerRepository cr;
	
	@GetMapping("/")
	public String ShowHome(Model model) {
		model.addAttribute("customer", cr.findAll());
		long count = cr.count();
		model.addAttribute("count",count);
		return"index";		
	}
	
	@GetMapping("/new")
	public String showAdd(){
		return "custForm";
	}
	
	@PostMapping("/add")
	public String addMoreCustomers(
		@RequestParam("id") Integer id,
		@RequestParam("name") String name,
		@RequestParam("email") String email,
		@RequestParam("location") String loc,
		@RequestParam("balance") Float bal
		) {
		Customer customer = new Customer(id,name,email,loc,bal);
		cr.save(customer);
		return "redirect:/";
	}
	
	@GetMapping("/view")
	public List<Customer> show(){
		List<Customer> lc= (List<Customer>) cr.findAll();
		return lc;
	}
	
	@GetMapping("/remove/{id}")
	public String removeRec(@PathVariable("id") Integer id) {
		cr.deleteById(id);
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id,Model m) {
		Optional<Customer> customer =cr.findById(id);   // fetching the record for the given id
		m.addAttribute("customer", customer);
		return "editform";		
	}
	@PutMapping("/save")
	public String saveData(@Valid Customer customer) {
		cr.save(customer);
		return "redirect:/";
	}
}
