# Megadrum Manager (MDM)
This is the multiplatform manager for the MegaDrum trigger-to-MIDI device. MegaDrum is a simple and easy to build yet very powerful MIDI drum trigger. It can be used with many varieties of Edrum kits/pads/cymbals and outputs MIDI signals which can be fed to a computer with drum sampling software,e.g. BFD, Toontrack, Addictive Drums, to produce wide variety of drum sounds or it can be connected to a drum machine. It can be compared with Roland TMC-6 but with more inputs and more capabilities. See more info at http://www.megadrum.info.

## Development environment
To contribute to MDM, please set up your development environment first like this:

1. Install Eclipse Java.
1. Within Eclipse, from the Help->Install New Software menu, install WindowBuilder and SWT Designer.
1. Fork this repository on GitHub.
1. Pull your forked repository into your Eclipse Workspace (using the Eclipse Git plugin or your separately installed Git client). Create a 'lib' folder in the root of the pulled repository.
1. Download the following binary archives from the Apache Archives (http://archive.apache.org/dist/commons/) and put them into the lib folder:
	* commons-collections-3.2.1-bin.zip
	* commons-configuration-1.8-bin.zip
	* commons-lang-2.6-bin.zip
	* commons-logging-1.1.1-bin.zip
1. You also have to put the appbundler-1.0.jar into the lib folder. Download it from here: https://java.net/projects/appbundler/downloads

## Compiling Megadrum Manager
1. In Eclipse, open the Ant View.
1. Add the build.xml buildfile to the view.
1. Run the Ant target called 'build'. This is the default target. It will build a bunch of class files in the bin folder.


## Creating a runnable JAR file.
1. In Eclipse, create a new Run Configuration for MDM. It should be in the 'Java Application' group. On the Main tab set the Main Class to 'gui.Main_window'. Other settings can remain at their defaults.
1. To export a runnable JAR file, use File->Export->Java->Runnable JAR file. Select your Launch configuration that you've just created. Enter a name for the JAR file. Leave 'Library handling' at 'Extract required libraries into generated JAR' and click 'Finish'.