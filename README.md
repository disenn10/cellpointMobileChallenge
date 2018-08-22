# cellpointMobileChallenge
revised code
In regards to the bug,

Problem:

The main activity was making a request to the server in order to display all the different languages in the requested repository, out of the 30 items gitHub Api shows per page(default).

Although the count for each section was correct, the number of the repositories displayed inside each section was wrong. 
This was due to the fact that I was making a request to a different url, and not out of the 30 items previously requested.

Solution:

I just had to modify the extractdata method in the Utils function. The method is now using the same url previously used to put each languages in different sections.
This also fixed the problem of having the empty UI.
