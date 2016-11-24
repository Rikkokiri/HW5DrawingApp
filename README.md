# HW5DrawingApp

##Instructions and requirements for the app:

This homework exercises your abilities to fetch a picture from the camera, handle the multitouch event and use Canvas to create a customized image. The core tasks ask that you appropriately use camera, OnTouchListener and Canvas to take a picture, and use the multi-touch to paint on the picture. The application to be developed is a picture-painting app: user takes a picture with a camera first, and draws lines and icons on the picture with multi-touch and gestures. The requirements are:

1. (IMPLEMENTED) The app must contain two Activities. The first Activity only has one button in the middle. After clicking the button, the camera starts and the user takes a picture. The returned picture must be passed to the second Activity. The picture must be a full resolution picture (depends on the resolution of the camera), not the thumbnail.

2. (IMPLEMENTED) The second activity shows the received picture. At the bottom of the screen, there must be 6 buttons. The three buttons on the top show the three colors of the paint. The buttons in the bottom row are undo, clear and done buttons.

3. The second activity must support multi-touch drawing. After the user selects the paint color, touches and moves the finger on the screen, the app must draw the trace of the finger movement on top of the picture, with the stroke of selected color. The traces must follow user’s fingers in real-time, without significant delay. The app must enable single touch drawing, as well as multi-touch drawing (show multiple lines while user moves multiple fingers on the screen).

4. The app must support adding icons by double tapping and long pressing. Double tapping the screen once must add one icon. Long pressing must add another different icon. The icon must be a drawable image. E.g. a star, a hokiebird, VT logo or an avatar of your favorite cartoon character.

5. The app must support undo the drawing and clearing the canvas, by pressing the corresponding button. Pressing the undo button once will cancel the last finger drawing. Clicking the clear button will remove all the lines (Paths) and icons.

6. After the user finishes editing, the user returns to the first Activity by clicking the Done button. The app must allow the user to take another picture, and paint it again.


##Bonus requirements (for bonus points!):

1. The undo will cancel the last action, which includes both line drawing and icon adding. For example, if the user draws a red line, adds a star, and then draws a green line, the order of undoing should be reversed: remove the green line, remove the star and then remove the red line.
2. When the user touches the finger on the screen, the app plays the sound of pencil writing. Playing the sound must not block the user interaction.
3. Adding animation to the added icons (e.g. shaking, rotating, blinking or flashing). The animation must play repeatedly while the user paints the picture, and not block the user interaction.
4. After the user clicks done, the image with all paintings and icons is saved as a picture in the photo gallery. The picture can be viewed with the gallery app.

##Rubric
- App can take a picture and show it in the second activity 20%
- App enables multi-touch drawing, with the selected color 20%
- App supports adding different icons by double-tapping and long-pressing gesture 20%
- App supports the undoing the line-drawing and clearing the canvas 20%
- After finishing one picture, the user can take another picture and edit it again 10%
- Bug free 10%

##Bonus points:
- Animated icons 5%
- Save to gallery 5%
- Undo both line-drawing and icon-adding 5%
- Pencil sound 5%
