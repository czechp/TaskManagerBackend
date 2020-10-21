package com.pczech.taskmanager.controller;

import com.pczech.taskmanager.domain.Goal;
import com.pczech.taskmanager.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController()
@RequestMapping("/api/goals")
@CrossOrigin("*")
@Validated()
public class GoalController {
    private GoalService goalService;


    @Autowired()
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PutMapping("/{goalId}")
    @ResponseStatus(HttpStatus.OK)
    public Goal modify(@PathVariable(name = "goalId") @Min(1L) long id,
                       @RequestBody() Goal goal){
        return goalService.modify(id, goal);
    }

    @DeleteMapping("/{goalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "goalId") @Min(1L) long id){
        goalService.deleteById(id);
    }
}
