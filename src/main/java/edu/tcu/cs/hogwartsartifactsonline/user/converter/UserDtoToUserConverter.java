@Component
public class UserDtoToUserConverter implements Converter<UserDto, HogwartsUser> {

    @Override
    public HogwartsUser convert(UserDto source) {
        HogwartsUser user = new HogwartsUser();
        user.setId(source.id());
        user.setUsername(source.username());
        user.setEnabled(source.enabled());
        user.setRoles(source.roles());
        return user;
    }
}
