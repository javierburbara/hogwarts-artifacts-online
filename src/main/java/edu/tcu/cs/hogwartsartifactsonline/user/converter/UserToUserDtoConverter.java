@Component
public class UserToUserDtoConverter implements Converter<HogwartsUser, UserDto> {

    @Override
    public UserDto convert(HogwartsUser source) {
        return new UserDto(
                source.getId(),
                source.getUsername(),
                source.isEnabled(),
                source.getRoles()
        );
    }
}
