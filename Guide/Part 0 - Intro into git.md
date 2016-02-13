![Git Logo](https://git-scm.com/images/logo@2x.png)

#Part 1
####What is git and why do I need it?
Git is version control software, or a set of command-line tools meant to make the task of version
control simpler. For example when you are working on a project with multiple people git will
keep track of each persons changes and allow them to seamlessly (somtimes) merge the work of one
person with another's through a few commands. Another thing git does is keep track of version or progress
so that at any time if something breaks you can revert back.

**Note: git is not Github, Github is a cloud service/interface for git**

####Installing git

####Essential commands
```
git init
```
This command will initialize a git repository in the current directory. You will want to cd into your projects
root directory and then use the git init command.

```
git clone https://github.com/idappthat/MavYaks.git
```
The git clone command allows you to clone a remote reposiotry so that you can have the project
on your computer locally and make changes. Note the git clone command clones the repository to your current 
directory so you will want to cd to a good spot, like a Projects folder for example. git clone takes one argument 
which is the url of the repository.

```
cd /path/to/project
git pull
```
The git pull command pulls anything new that is in the master copy on the remote repository.
Ex. If you have already cloned the MavYaks project then you can cd to that directory and use the
git pull command to pull the most recent changes. 

**Note: In order to pull you must add and commit any changes you have made to your local repository before you can pull**


```
git add .
```
The git add command adds files to the list of things that can be commited. The add command must be used before
you can commit changes. The git add commmand takes the arguments of the files you want to add. Most of the time
you can just use the `.` operator to add all files and git will all all the files that have changed to the commit.


```
git commit -m "My Custom Message"
```
The git commit command allows you to commit your changes and tells git to save the changes to the local repository and
keep a record of those changes. You have to give the git commit command a message argument with the `-m "cool new feature"`
syntax you put your changes inside the quotes, quotes are required.


```
git push origin master
```
The git push command allows you to push your changes to a remote repository so that others can acces what you have
done, for simplicity you can push to the master branch `origin` is the name of the remote repository, by default GitHub
names their remote repositories origin. The `master` argument specifies the branch you wish to push to, the 
master branch is the main branch, although there can be other branches we won't go into detail on that, since that is 
more advanced.
