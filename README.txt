README file for Software I project
Author: Chris Stephenson

Project Package/Folder Structure:
	main.java - contains main, util and validation classes
	main.java.controller - contains FXML controller classes
	main.java.model - contains object models from UML diagram
	main.resources.view - contains FXML UI files

Notes:
- Part and Product windows display as modal windows in front of the main application window.

- I combined the "Add" and "Modify" views, as well as the controllers.  Originally, I started with separate views and controllers for the AddPart/ModifyPart and AddProduct/ModifyProduct, but there was too much duplicated code.  Also, the views were pretty much identical, so I decided to combine them into one for Part and one for Product.

- The updateProduct and updatePart methods are commented out in the Inventory class.  Since I'm using FXML controller classes, this functionality is handled in the ProductController and PartController classes respectively. 

- I elected for the part type (inhouse/outsourced) to be fixed after it is initially save as a new part.










