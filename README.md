# Tableau-ization
The main purpose of this project is to transform any source code into an stylized picture.

Here's a few example of file transformed by our project: 

<p align="center">
<a href="url"><img src="http://img11.hostingpics.net/pics/393851Agentjava.png" height="275"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/240823AgentLucasjava.png" height="275"></a>
<a href="url"><img src="http://img11.hostingpics.net/pics/146775AgentDavidjava.png" height="275"></a>
</p>

## How it works

So, our project take every files and folder in the Input folder and it'll transform every file in it into an image in the Output folder.

### The parsing

A file is parsed in order to generated an image. We take the text and replace all non-alphanumerics character by a space. In order to avoid noise in the output file, we delete all commentaries present in the code with a generic class that can be personnalized. For now, we delete commentaries for a few different langages, the list can be found and improved [here](src/processing/Commentary.java).

Now, we count the occurence of every words of the file and order them by order of apparition.

### The agents

To draw the picture, we use a [multi agent system](https://en.wikipedia.org/wiki/Multi-agent_system). The "Particule" agent will move and put their color on the background agent. A Background agent represent a pixel of the generated picture.

The agent are placed depending of the words found in the input file. The color is determined by the Hash of the word. And the number of agent for a specified word is determined by the number of occurence of this particular word. The more a word appear, the more his vector will be large and will last longer in the generated picture.

## How do I use it ?

## Credits

This project has been written by Lucas Delvallet and Florian Doublet.




