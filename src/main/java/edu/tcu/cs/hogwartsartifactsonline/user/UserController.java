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
    public Result findAll() { ... }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) { ... }

    @PostMapping
    public Result add(@RequestBody HogwartsUser user) { ... }

    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody UserDto dto) { ... }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) { ... }