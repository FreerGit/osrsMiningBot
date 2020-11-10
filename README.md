# osrsMiningBot

![Alt Text](https://github.com/FreerGit/osrsMiningBot/blob/master/initialAI.gif)

An oldschool Runescape bot made with tensorflow.

## Future work:
- As you can see in the gif, some frames the AI does not recognize the ores. This poses a problem with the bot since it can be hard know to the next step for the bot to take. Object tracking will be needed, essentially if a ore is detected 7/10 frames we can be pretty sure it's an actual ore and not a fluke. But also keep track of all the ores so a path for the bot can be produced. So far I have looked into openCV's object detecting, maybe something else? Alot of testing needed.

- Right now the bot does nothing noteworthy other than recognizing that there are objects on the screen (iron ore, empty ore). But since I have produced the bounding boxes around the object also means i can for example click on the middle of the box. Combine this with object tracking and we have a miner!

- I have tried to get the frame rate up but this is a rather hard task it seems. Currently two different threads are running, one "AI" thread and one GUI thread to parallelize the work. But as of now i get only around 20-30 fps as can be seen in the gif. im certain the code can be optimzed alot further! 

- The object detection certainly isn't as reliable as i would like it to be. I would probably need to train it more with a larger dataset. I should also look into different pre-trained models to retrain. https://github.com/tensorflow/models/tree/master/research/object_detection

- I also would like to put this whole project in a docker container. It is REALLY hard to get this project running on a different computer, it requires alot of different software to be downloaded. It also requires a compatible GPU, long story short I can currently only run this on my own computer as of now.


## DIRS:


**Addons** is for additional software such as labelImg to create custom labeled images to train on.

**Scripts** is mainly for converting xml -> csv, csv -> .record

**Workplace** contains images, pre trained model(startpoint, checkpoint), annotations that were created with the scripts and the training folder.


## Tensorflow and guides

**Installation and setting up custom model**

https://tensorflow-object-detection-api-tutorial.readthedocs.io/en/latest/index.html

***Commands for my own use***

**to train:** python model_main.py --alsologtostderr --model_dir=training/ --pipeline_config_path=pre-trained-model/ssd_mobilenet_v1_coco.config

**to view the training:** tensorboard --logdir=training
And then localhost:
http://fredrik-desktop:6006/
