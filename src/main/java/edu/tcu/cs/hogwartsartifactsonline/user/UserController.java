package edu.tcu.cs.hogwartsartifactsonline.user;

import edu.tcu.cs.hogwartsartifactsonline.system.Result;
import edu.tcu.cs.hogwartsartifactsonline.system.StatusCode;
import edu.tcu.cs.hogwartsartifactsonline.user.converter.UserDtoToUserConverter;
import edu.tcu.cs.hogwartsartifactsonline.user.converter.UserToUserDtoConverter;
import edu.tcu.cs.hogwartsartifactsonline.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {

    private final UserService service;
    private final UserToUserDtoConverter toDto;
    private final UserDtoToUserConverter toEntity;

    public UserController(UserService service,
                          UserToUserDtoConverter toDto,
                          UserDtoToUserConverter toEntity) {
        this.service = service;
        this.toDto = toDto;
        this.toEntity = toEntity;
    }

    @GetMapping
    public Result findAll() {
        List<UserDto> dtos = service.findAll().stream()
                .map(toDto::convert)
                .toList();

        return new Result(true, StatusCode.SUCCESS, "Find All Success", dtos);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        return new Result(true, StatusCode.SUCCESS, "Find One Success",
                toDto.convert(service.findById(id)));
    }

    @PostMapping
    public Result add(@Valid @RequestBody HogwartsUser user) {
        return new Result(true, StatusCode.SUCCESS, "Add Success",
                toDto.convert(service.save(user)));
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id,
                         @Valid @RequestBody UserDto dto) {

        HogwartsUser update = toEntity.convert(dto);

        return new Result(true, StatusCode.SUCCESS, "Update Success",
                toDto.convert(service.update(id, update)));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        service.delete(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
