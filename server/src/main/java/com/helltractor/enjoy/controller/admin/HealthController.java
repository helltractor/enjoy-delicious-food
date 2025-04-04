package com.helltractor.enjoy.controller.admin;

import com.helltractor.enjoy.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("actuator")
@Api(tags = "健康检查")
public class HealthController {

    @GetMapping("/health")
    @ApiOperation(value = "健康检查")
    public Result<String> health() {
        return Result.success("ok");
    }
}