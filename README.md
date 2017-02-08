# Tableau-ization
The purpose of this project is to transform any source code into an stylized picture.

Here's a few example of file transformed by our project: 

<p align="center">
<a href="url"><img src="http://img11.hostingpics.net/pics/393851Agentjava.png" height="275"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/240823AgentLucasjava.png" height="275"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/146775AgentDavidjava.png" height="275"></a>
</p>

## Why ?

An advantage of this project, is to detect code plagiarism in the output pictures. Here's an example : 

<p align="center">
<a href="url"><img src="http://img11.hostingpics.net/pics/568310M3DS1cpp.png" height="150"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/876120M3DS2cpp.png" height="150"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/905121M3DS3cpp.png" height="150"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/757448M3DS4cpp.png" height="150"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/804605M3DS5cpp.png" height="150"></a>
</p>

<p align="center">
<a href="url"><img src="http://img11.hostingpics.net/pics/824987M3DS6cpp.png" height="150"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/502158M3DS7cpp.png" height="150"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/133119M3DS8cpp.png" height="150"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/290841M3DS9cpp.png" height="150"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/334868M3DS10cpp.png" height="150"></a>
</p>

These are all students source code for a same exercice. Do you see, how, some of these are alike ?


## How it works

So, our project take every files and folder in the Input folder and it'll transform every file in it into an image in the Output folder.

### The parsing

A file is parsed in order to generated an image. We take the text and replace all non-alphanumerics character by a space. In order to avoid noise in the output file, we delete all commentaries present in the code with a generic class that can be personnalized. For now, we delete commentaries for a few different langages, the list can be found and improved [here](src/processing/Commentary.java).

Now, we count the occurence of every words of the file and order them by order of apparition.

### The agents

To draw the picture, we use a [multi agent system](https://en.wikipedia.org/wiki/Multi-agent_system). The "Particule" agent will move and put their color on the background agent. A Background agent represent a pixel of the generated picture.

The agent are placed depending of the words found in the input file. The color is determined by the Hash of the word. And the number of agent for a specified word is determined by the number of occurence of this particular word. The more a word appear, the more his vector will be large and will last longer in the generated picture.

## How do I use it ?

The main can be launched in two different mode :

- Default mode, which generate a beautiful picture of your code present in the Input file.
- Visual mode, which does the same, but you can see how it's generated. Here's an example : 
<p align="center">
<a href="url"><img src="https://media.giphy.com/media/l0ExdKgFY2GxlQPK0/source.gif" height="275"></a>
</p>


## Credits

This project has been written by Lucas Delvallet and Florian Doublet.




