@PostMapping("/login")
public Result getLoginInfo(Authentication authentication) {
    return new Result(true, StatusCode.SUCCESS,
            "User Info and Token",
            authService.createLoginInfo(authentication));
}