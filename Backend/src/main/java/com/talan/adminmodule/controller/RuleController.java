package com.talan.adminmodule.controller;

import com.talan.adminmodule.dto.RuleDto;
import com.talan.adminmodule.dto.RuleModificationDto;
import com.talan.adminmodule.service.RuleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@CrossOrigin("*")
@Tag(name = "Rule")

public class RuleController {

    private final RuleService ruleService;

    @Autowired
    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public ResponseEntity<RuleDto> saveRule(@RequestBody RuleDto ruleDto) {
        RuleDto savedRule = ruleService.save(ruleDto);
        return new ResponseEntity<>(savedRule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RuleDto> updateStatus(@PathVariable("id") Integer id, @RequestParam boolean enabled) {
        RuleDto updatedRule = ruleService.updateStatus(id, enabled);
        return new ResponseEntity<>(updatedRule, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable("id") Integer id) {
        ruleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{modDescription}/{id}/{modifiedBy}")
    public ResponseEntity<RuleDto> updateRule(@PathVariable("modifiedBy") Integer modifiedBy ,@PathVariable("id") Integer id,@PathVariable("modDescription") String modDescription, @RequestBody RuleDto updatedRuleDto) {
        RuleDto updatedRule = ruleService.updateRule(id, updatedRuleDto,modDescription , modifiedBy);
        if (updatedRule != null) {
            return new ResponseEntity<>(updatedRule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleDto> findRuleById(@PathVariable("id") Integer id) {
        RuleDto ruleDto = ruleService.findById(id);
        if (ruleDto != null) {
            return new ResponseEntity<>(ruleDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}/history")
    public ResponseEntity<List<RuleModificationDto>> getModificationsByRuleId(@PathVariable Integer id) {
        List<RuleModificationDto> modifications = ruleService.getModificationsByRuleId(id);

        return new ResponseEntity<>(modifications, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<RuleDto>> findAllRules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<RuleDto> rules = ruleService.findAll(page, size);
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RuleDto>> searchRules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query) {
        Page<RuleDto> rules = ruleService.searchRules(page, size, query);
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }
}