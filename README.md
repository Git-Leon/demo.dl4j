# DL4J & OpenCV Demo

## Software Dependencies
* To run this project you must install [opencv]().
* If you are running OSX, it is recommended that you
	1. Install XCode
	2. Install HomeBrew
	3. Install OpenCV

### Installing XCode

### Installing HomeBrew

### Installing OpenCV
* `brew` an installation of `opencv` version 3.
    * `brew install opencv@3`
* Edit _OpenCV formula_ to enable java support.
    * `brew edit opencv`
        * change property from `-DBUILD_opencv_java=OFF` to `-DBUILD_opencv_java=ON`
* `brew` an installation of `opencv` from local source
    * `brew install --build-from-source opencv`

### Checking OpenCV java installation
* Ensure a version of OpenCV can be found in your local machine directory. (Edit the directory version)
    * `/usr/local/Cellar/opencv/3.x.x/share/OpenCV/java/`