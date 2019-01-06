[Commit message](#commit-message)

[Branch naming convention](#branch-naming-convention)

## Commit message
  * Each commit message should start with prefix which contains hash and issue number, for example "#132"
  * Commits which doesn't affect project build status like updating docs files "README.md" doesn't have to build by Travis CI. To do that commit message should contain sufix "[skip travis]" 

## Branch naming convention
Try to name your branch based on type of issue related to it, for example:
    
  * Feature - Prefix "feature/", issue number and some short description, for example:
    
    Branch name for feature related to integration with Travis CI and with number 113.
    "feature/113_travis_integration"
  * Bugfix - Prefix "bugfix/", issue number and some short description, for example:
  
    Branch name for bugfix related to NullPointer exception and with number 227.
    "bugfix/227_nullpointer_exception"
    
    