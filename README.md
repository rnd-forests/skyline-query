# Spatial Skyline Query Algorithms

A Java Implementation of Spatial Skyline Query Algorithms.

## References
Algorithms are built based on descriptions in the following research papers:
* [An Optimal and Progressive Algorithm for Skyline Queries](http://www.cs.ust.hk/~dimitris/publications.html) by Dimitris Papadias, Yufei Tao, Greg Fu, and Bernhard Seeger.
* [Shooting Stars in the Sky: An Online Algorithm for Skyline Queries](http://www.informatik.uni-trier.de/~ley/pers/hd/k/Kossmann:Donald) by Donald Kossmann, Frank Ramsak, and Steffen Rost.

## Algorithms
* Branch and Bound Skyline (BBS) 
* Nearest Neighbor (NN)

## Libraries
* [RTree](https://github.com/davidmoten/rtree) by [Dave Moten](https://github.com/davidmoten).
* [Java Standard Libraries - StdDraw](http://introcs.cs.princeton.edu/java/stdlib/StdDraw.java.html) by [Robert Sedgewick](http://www.cs.princeton.edu/~rs/) and [Kevin Wayne](http://www.cs.princeton.edu/~wayne/contact/).

## Sample Data
* [Data files](https://github.com/dkanellis/SkySpark/tree/master/data/datasets) are taken from the [SkySpark](https://github.com/dkanellis/SkySpark) repository of [Dimitris Kanellis](https://github.com/dkanellis).
* [Data files](https://github.com/dkanellis/SkySpark/tree/master/data/datasets) are imported into MySQL database, and queried later for testing algorithms.

## Demonstration
##### GUI application
![GUI application](http://i1368.photobucket.com/albums/ag182/vinhnguyenict/2015-09-01_1424_zpskdzddbbu.png)

##### Skyline Visualization
###### Anti-correlated 2D points dataset
![Anti-correlated](http://i1368.photobucket.com/albums/ag182/vinhnguyenict/2015-09-13_1730_zpspo9lk2wn.png)

###### Correlated 2D points dataset
![Correlated](http://i1368.photobucket.com/albums/ag182/vinhnguyenict/2015-09-13_1733_zpsavcc0d2v.png)

###### Uniformly Distributed 2D points dataset
![Uniform](http://i1368.photobucket.com/albums/ag182/vinhnguyenict/2015-09-13_1734_zpsbpdfkaep.png)

## License
This application is open-source released under [MIT license](http://opensource.org/licenses/MIT).

All 3rd party open sourced libraries distributed with this application are still under their own license.


## Authors
* [Vinh Nguyen](https://github.com/vinhnguyen-fly)
* [Kien Hoang](https://github.com/goddesss)
