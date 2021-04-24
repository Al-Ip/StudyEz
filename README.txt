There is an attached read me file contained within the projects directoty, it is of type MD and contains images 
that may help with setting up the enviroment. If the images do not load when opend in the text pad please click 
this link to go to my github page for the project and see the aformentioned README.md file in the browser.

https://github.com/Al-Ip/StudyEz

==================================

# StudyEz - FYP 

This project pertains to the final year project completed in Limerick Intitute of Technology. The setps below will idicate what is required in order to be able to run and build the applicaiton on your own machine. Note that while this in therory this should work for everyone, different machines may encounter some issues, primaryly the machines that already have a version of Android Studio installed. It is reccommended to get the latest version and latest gradle updates.

### Step 1 - Download Android Studio
This project is develoepd using Android Studio as the IDE and requires it in order to be able to build and run the app. The IDE can be found online or by clicking this link to download https://developer.android.com/studio. Once it is downloaded and installed the moment you open the IDE you may be prompted to perform updates, accept all the updates, if not go to step 2.

### Step 2 - Import the Project
Simply import the files like you would any other project. Once the project has been imported you may be asked to update various components in the botton right corner of the screen, accept all of these updates.

### Step 3 - Adding A Virtual Device (AVD)
Since this is an android applicaiton that requires an android compatible smartphone, the use of an AVD needs to be downloaded and installed. You may use a physical device in some cases, but more issues can arise by trying to setup a physical device than by just using an AVD. 
The AVD manager can be found here
![image](https://user-images.githubusercontent.com/43743759/115952070-00e99300-a4dc-11eb-934b-0394edad612b.png)

Once in the AVD manager, click 'Create Virtual Device'. This will take you through the process of creating a new virtual device, it does not matter what configuration you select but once you get into the system image make sure it is **NOT** under **Oreo** (API Level 26). I suggest dowloading and using **Pie** (API Level 28) as the system image.
![image](https://user-images.githubusercontent.com/43743759/115952465-f9c38480-a4dd-11eb-8344-5a2e7704e82d.png)

Once the image has been downloaded click next and you'll be brought into the last page. Put in the AVD name and click finish.
You should now see the newly added AVD in beside the run button ![image](https://user-images.githubusercontent.com/43743759/115952703-7145e380-a4df-11eb-9deb-ebd4d0439b4f.png)

### Step 4 - SDK Manager
In case issues may arise and the applicaiton cannot be run, I have included my SDK configurations below. The SDK manager can be found beside the AVD manager and can be tinkered with accordingly. I would suggest trying to run and build the application and get it to display on the emulator first, before coming to the SDK manager.
![image](https://user-images.githubusercontent.com/43743759/115953183-cf73c600-a4e1-11eb-8187-7a1276a9f135.png)

### Step 5 - Run the App
Now that you have an AVD installed, you can press the Run button beside the created AVD and begin running the app. The build process will happen automatically and an emulator should open automatically also. This emulator will launch the app after a few moments, you can set it so that the emulator opens directly inside the IDE like so but its fine if it opens a sperate window.

![image](https://user-images.githubusercontent.com/43743759/115952815-224c7e00-a4e0-11eb-9ce9-6f966811b668.png)

### Step 6 - Using the App
Once the applicaiton is up and running on your AVD you can start using the app in its currrent and most updated state. No other confguration is needed, no admin accounts etc. Put in an abitrary email that has a Gmail extention i.e. 'TestingApp@Gmail.com' in the registration screen and you'll be able to progress into the app.


