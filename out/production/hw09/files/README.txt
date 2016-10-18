Model Design:
	* Song pieces are represented as a TreeMap with Integers as keys and ArrayLists of INotes as values. The Integer keys are the beats in a music piece and the ArrayLists are the notes at that beat.
	* The musicEditorOutput() method returns a String of the console rendering of a music piece. It uses a StringBuilder and appends to it while iterating through a loop to create the text rendering.
	* There is a Builder included to build objects of the model.
	* In order to access the private components of the model we made getter methods for everything we called on rather than calling directly on the reference. 
	*We added a level of abstraction by putting in an interface for the Note class. 

View Design:
	* There are three different types of views: Console, GUI, and MIDI. There is a view interface, IView, and separate implementations of it to represent the different types of views.
	* The ConsoleViewImpl class is for representing the text view of a music piece. It takes in an IMusicEditor as an input argument. It overrides the initialize() method from the interface to simply call the musicEditorOutput() method of the IMusicEditor and prints the returned String to System.out to display the output.
	* The MidiViewImpl class is for representing the audio view of a music piece. It takes in an IMusicEditor as an input argument. It overrides the initialize() method from the interface to generate audio of the music piece. It uses a Timer and its scheduleAtFixedRate() method to play each note. It first gets the tempo of the piece from the model and converts it to seconds. If the tempo is less than 1000, it will round the quotient result up to 1 since the schedule method of the Timer takes in a tempo argument as a Long, not a Double. The schedule will run the updateTime() method with calls the playNote() method and updates the beat number. The playNote() method goes through the music piece and sends MIDI messages to generate the audio.
	* The GuiViewFrame class is for outputting the visual view of a music piece. It takes in an IMusicEditor as an input argument. It overrides the initialize() method from the interface to set constraints on the visual output and to make it visible. The displayed panel is set to the ConcreteGuiViewFrame which is where the calculations to paint the different parts of the visual output is made. 
	* The ConcreteGuiViewFrame class draws the GUI view for a music piece. It takes in an IMusicEditor as an input argument. It overrides the paint component from JPanel to paint images relevant to our music visual output. There are 4 main methods that paint the pitch header, beat header, the played notes, and the grids. The printPitches method goes through the list of notes in the header and displays it in the right x and y coordinates using the drawString function. The printBeatHeader method iterates from the first to last beat and prints a beat every 4 beats at the right x and y coordinates. The printPlayedNotes method goes through the list of song notes and prints each note location based off of its start time and pitch value. The printGrid method iterates through the pitches and then iterates through the beats to draw a rectangular boxes per pitch every 4 beats. It was easier to paint each piece in separate methods and call on each method in the paintCompenent method rather than having one large method for the entire output. 
	* The ViewFactory class is used to be able create the views. It has a generateView() method that takes in a String of the specific view to return. The three valid options that can be used as inputs are: "console", "gui", and "midi". An exception will be thrown if any other input is used.

Changes to Design:
	* Added a method to the model that gets the ArrayList of notes at a specific beat
	* Changed the musicEditorOutput() method to use a StringBuilder to improve performance



----------------------------------------HW07----------------------------------------

Changes to Design:
	* Console rendering method implementation moved from the MusicEditorModel class to the ConsoleViewImpl class
	* updateTime() method added to the IView interface so that views affected by time can use
	* A GuiView interface was added that extends the IView interface. This is for adding GUI-specific methods.
	* The GuiViewFrame now has the ability to scroll and add/remove notes.

Controller:
	* The Controller class is an implementation of an IController and adds various functions that can be used to control the views. It has a KeyListener and MouseListener to give functionability to use the keyboard and mouse as inputs. It also methods to add and remove MouseListeners and getter methods for both the KeyListener and MouseListener. It installs various key handler functions that are run when certain keys are pressed.
		- "P" key will pause the view
		- "R" key will resume the paused view
		- "HOME" key will bring the view back to the start
		- "END" key will bring the view to the end
		- "LEFT" key will scroll the view to the left
		- "RIGHT" key will scroll the view to the right
		- "UP" key will scroll the view upwards
		- "DOWN" key will scroll the view downwards
	* The KeyboardHandler class has three maps with Integers as keys and Runnables as values. The first map is for keys typed, the second is for keys pressed, and the third is for keys released. Included in the class are methods to run the Runnables attributed to different KeyEvents in the maps, along with methods that add other key functions and getter methods for the maps.
	* The MouseHandler class has three maps with Integers as keys and Runnables as values. The first map is for mouse clicked, the second is for mouse pressed, and the third is for mouse released. Included in the class are methods to run the Runnables attributed to differnt MouseEvents in the maps, along with methods that add other mouse functions and getter methods for the maps. There is also methods to retrieve the coordinates of the mouse during the three actions.
	* The GUI view now has the ability to add and remove notes using the controller. With our design, there is no need to use any KeyboardHandler or MouseHandler to use these functions. Rather, we enhanced the visual views to allow the user to enter values for the notes to be added or removed. This is easier to use than having the user use complicated combinations of keyboard and mouse actions.