## CodeFellowship Login

#### Feature Tasks

Build an app that allows users to log into CodeFellowship.

-Login page
-Login page link to signup page
-ApplicationUser username, password (will be hashed using BCrypt), firstName, lastName, dateOfBirth,
bio, and any other fields that might be useful
-All fields set at signup. Not editable at any other time
-Site will allow users to create an ApplicationUser on the “sign up” page
-Controller will have an @Autowired private PasswordEncoder passwordEncoder; and use that to run
passwordEncoder.encode(password) before saving the password into the new user.
-Using the cheat sheet above in “Resources”, users will be able to log in to the app
-Site will have a homepage at the root route (/) with basic information about the site
-This page will have links to login and signup if the user is NOT logged in
-Link to logout if the user IS logged in
-Ensure that users can log out and are redirected to the home page or login page.
-When user is logged in, app will display user’s username on every page (probably in the heading)
-Ensure homepage, login, and registration routes are accessible to non-logged in users
-Site will be well-styled and attractive
-Site will use templates to display its information
-User registration will log users into app automatically

[Spring Security cheat sheet](https://codefellows.github.io/code-401-java-guide/curriculum/SpringSecurityCheatSheet.html)