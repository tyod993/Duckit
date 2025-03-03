Welcome to Duckit!

This app follows a pretty basic architecture that should be easy to follow if you have spent any time with compose. 
The modern compose architecture is based on MVI. In this app this is what we will be using. I made an attempt at 
creating clear and distict lines between architectural layers like the Data layer and Domain. In its current state 
everything works except the up/down like feature. I am currently getting a 403 when making that attempt and will
work on it after this initial commit. 

This app is close to deployable but there are a few things that I am aware of that still need to be done for this to 
be prodution ready. The first being Tests, in the interest of time I decided to omit them for the time being. There
are a few areas that should be tested. The Priority being the API layer. Another thing that makes this app not ready 
for release is the use experince is not 100% complete. There are some edge cases that have not been handled yet.
Along with the need for the "New Post" feature. When the user presses a like button but isnt logged in there should 
be a dialog that pops up one time to let them know to sign up. Another thing that I didnt implement due to it being
difficult for us to test all of the flows is user token cacheing. This would allow users not to log in every time. 
But for this to work we would need a logout button as well. Another issue that I found that I would like to fix if
I had more time would be some of the images do not load any more for many number of reasons and we dont have a 
default state for the image when it doesnt load properly. 

If you have any comments or questions please feel free to open a PR and ask all your questions. I will answer them as soon as I can.
