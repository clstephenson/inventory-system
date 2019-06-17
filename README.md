This was a basic JavaFX project that was course requirement for my degree.

#### Project Package/Folder Structure
- src directory - source files for application
  - main.java - contains main, util and validation classes
  - main.java.controller - contains FXML controller classes
  - main.java.model - contains object models from UML diagram
  - main.resources.view - contains FXML UI files
- dist directory - documentation (javadoc) 

#### Notes
- Part and Product windows display as modal windows in front of the main application window.

- I combined the "Add" and "Modify" views, as well as the controllers.  Originally, I started with separate views and controllers for the AddPart/ModifyPart and AddProduct/ModifyProduct, but there was too much duplicated code.  Also, the views were pretty much identical, so I decided to combine them into one for Part and one for Product.

- The updateProduct and updatePart methods are commented out in the Inventory class.  Since I'm using FXML controller classes, this functionality is handled in the ProductController and PartController classes respectively. 

- I elected for the part type (inhouse/outsourced) to be fixed after it is initially save as a new part.

- Since many of the validations are the same with parts and products, I separated input validation and business rule exceptions into a separate Validator class with two subclasses extending that class.  An instance of a subclass is created by calling a static getValidator method of the parent class.  I think with more experience, this design could be improved.

- I wanted the price information to be formatted as currency (i.e. $ and commas).  For the table columns, I was able to use the setCellFactory method to set up the formatting.  For the input fields, I went a different route.  When the price field gets focus, the currency format is removed.  Then, when the price field loses focus, the format is automatically added.  I was able to get this to work by setting a listener on the focused property of the text field.  It proved to be a little challenging to keep number format exceptions being thrown.

- Searching... The search text boxes are set up to respond to the enter key.  After the search happens, the search text field receives focus, and the text is selected.  That allows the user to immediately start typing a new search.  If no results are found, then a message box displays.

- Sample data... there is a method in the Util class that is called to load sample data into the inventory.   






