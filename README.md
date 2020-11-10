# osrsMiningBot

Private repository to create my own custom object detection models.

## DIRS:

![Alt Text](https://github.com/FreerGit/osrsMiningBot/blob/master/initialAI.gif)

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
