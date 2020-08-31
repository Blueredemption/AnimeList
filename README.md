# AnimeList 

A lightweight application made with Swing, the goal of this project is to provide a tool for a very hands on approach to recording the anime a person has watched. In addition to the list like functionality, the program includes individual pages for each show, statistics, UI customization, and more.

The motivation behind this project is a frustration in the structure and implementation of similar services. Often they provide a list format to record when and what anime the user has watched, but the user doesn't get to choose exactly what that anime is or the details about it. By putting all the data entry in the hands of the user, it removes those limits, and the user decides how they want their list to be represented.

## Installation

Click [here]() to download the program. Once competed, move the .zip to the directory of choice and extract the files. Create a shortcut if you would like, but the executable file must stay with the resource files to function correctly. This program requires JRE 1.8 or newer to run.

## Usage

### Navigating the Program
Navigation about the program is done using the navigation tab on the right. It can be expanded and contracted by clicking on the buttons with three vertical lines.

![alt text](https://github.com/Blueredemption/AnimeList/blob/master/Images/Usage%20Tutorial/Main%20Page.png?raw=true)

![alt text](https://github.com/Blueredemption/AnimeList/blob/master/Images/Usage%20Tutorial/Popout%20Navigation.png?raw=true)

### Adding and Editing Anime

To add an anime, click the "+" / "New Anime" button on the navigation tab. Once clicked the program will create a new anime, add it to the list, and bring you directly to the editing page. Each button is self-explanatory. Once clicked, in most cases the user is prompted to fill the field through a combination of dropdowns, buttons, and text areas that appear in the bottom right portion of the window. In the case of adding an image, a file selector window appears. 

A few tips: When it comes to the notepad in the bottom left, for what is entered to stay there you must click the save button. If you navigate away and then back that text will be gone if not saved prior. Also, regarding the "Color" button, this overrides the default color for list entries. Not only does this change the color of the background and some components in this page, it also changes these for the entry in the list page. 

Finally, what is entered is ultimately up to you, though the more details the more accurate and useful the statistics page and sorting algorithms will be.

![alt text](https://github.com/Blueredemption/AnimeList/blob/master/Images/Usage%20Tutorial/Anime%20Page.png?raw=true)

### Using the List Page

To go to the list page, click the "≡" / "Anime List" button on the navigation tab. Once there you can view an individual anime by clicking the "View" button on the entry. You can also search the list by using the "Quick Search" text field, sort the list by using the buttons under the "Sort List By:" label, or filter the list by using the dropdowns under the "Filter List By:" label. In each anime entry there is a "Hidden?" checkbox. By default these anime are hidden in the list and statistics pages, but clicking the checkbox in the bottom left of this page lets you see them. 

Note: The filters and hidden options in the list page are connected to there counterparts in the statistics page. This means, for example, if you filter by anime watched in 2018 in the list page, that filter will be applied in the statistics page if you go there. The reset button removes these filters in both pages.

![alt text](https://github.com/Blueredemption/AnimeList/blob/master/Images/Usage%20Tutorial/List%20Page.png?raw=true)

### Using the Statistics Page

To go to the statistics page, click the "σ" / "Statistics" button on the navigation tab. Here you can see a breakdown of the anime you have entered by a variety of different categories. The more you add, the more interesting and inciteful information you can find. At the top of the page you can filter the anime just like in the list page. The dropdowns for each category are on scroll panes, so no anime goes unrepresented. You can navigate to an anime entry by clicking on the name in the dropdown. In the case of the "Favorite Anime #" dropdowns, you can select your favorites and their corresponding image will be displayed.

![alt text](https://github.com/Blueredemption/AnimeList/blob/master/Images/Usage%20Tutorial/Statistics%20Page.png?raw=true)

### Using the Notes Page

To go to the notes page, click the "✎" / "Notepad" button on the navigation tab. It is simply two text areas that you can use for whatever you want. Just make sure to save before navigating away.

![alt text](https://github.com/Blueredemption/AnimeList/blob/master/Images/Usage%20Tutorial/Notes%20Page.png?raw=true)

### Using the Settings Page

To go to the settings page, click the "⛭" / "Settings" button on the navigation tab. Here you can change the colors for everything in the program, or just choose between the presets. This is also where you can change the image displayed on the homescreen.

![alt text](https://github.com/Blueredemption/AnimeList/blob/master/Images/Usage%20Tutorial/Settings%20Page.png?raw=true)

## Usage Recommendations

If you are using this program for the first time and you don't know the dates of when you started or finished the anime you are entering, you should try to enter them in order of when you watched them, starting with the first. The algorithm for sorting by date started sorts by when they are added for anime that don't have start dates, so keep that in mind of you want more accurate ordering.

It is recommended that you back up the "Anime Objects" file if you want to make sure you don't lose all of your hard work from an *act of god*. This is where all of your anime entries are stored.

## Contributing

If you would like to contribute to this project feel free! There are many features that can be added, what has been done is enough for me specifically. 

Just make a pull request and/or ask me directly at cseisnor@gmail.com

## License
[MIT](https://choosealicense.com/licenses/mit/)
