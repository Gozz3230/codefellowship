## CodeFellowship Login

### Feature Tasks

Build an app that allows users to log into CodeFellowship.

#### Phase One

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

#### Phase Two

Allow users to log in to CodeFellowship, view user profiles, and create posts.

-Upon logging in, users taken to /myProfile route that displays their info
-Will include a default profile picture, which is the same for every user, and their basic information from
ApplicationUser
-Page that allows viewing data about a single ApplicationUser, at a route like /users/{id}
-Keep homepage, login, and registration routes accessible to non-logged in users. All other
routes should be limited to logged-in users
-Add a Post entity
  -Has a body and a createdAt timestamp
  -Logged-in user can create a Post, the post should belong to the user that created it.
  -User’s posts visible on their profile page
-Use reusable templates for information(At a minimum, it will have one Thymeleaf fragment that is
used on multiple pages)
-Site will have a non-whitelabel error handling page that lets the user know, at minimum, the error code and a
brief message about what went wrong

#### Phase Three

-Ensure that users can’t perform SQL injection or HTML injection with their posts
-Allow users to follow other users. Following a user means that their posts show up in the logged-in user’s feed, where
they can see what all of their followed users have posted recently
-Ensure there is some way (like a users index page) that a user can discover other users on the service
-On a user profile page that does NOT belong to the currently logged-in user, display a “Follow” button. When a user
clicks that follow button, the logged-in user is now following the viewed-profile-page user
note: this will require a self-join on ApplicationUsers
-A user can visit a url (like /feed) to view all posts from the users they follow
-Each post will have a link to the user profile of the user who wrote the post