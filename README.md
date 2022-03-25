# Vegetables Image Detector

![GitHub top language](https://img.shields.io/github/languages/top/stopasola/vegetables-image-detector)

## Specification

Given photographs of vegetables like onions, tomatoes, carrots, potatoes, apples and bananas. Identify and label which vegetables are present in the image.

<p align="center">
  <img style="width: 65%; height:65%;", src="https://user-images.githubusercontent.com/17886190/160183121-494d8642-b783-4c35-a87a-da0a9fb4caf6.png"/>
</p>

## Phases

#### Segmentation

It was used

- Sobel filter

<p align="center">
  <img style="width: 65%; height:65%;", src="https://user-images.githubusercontent.com/17886190/160184015-0baebbb7-2deb-4f0a-870c-32617d4e7cbb.png"/>
</p>

- Thresholding 

<p align="center">
  <img style="width: 65%; height:65%;", src="https://user-images.githubusercontent.com/17886190/160184059-e162d1ca-b37d-4977-9e91-6d03b4a9e551.png"/>
</p>

Resulting in a segmented image. Original image with N objects, create N images with one object.

<p align="center">
  <img style="width: 65%; height:65%;", src="https://user-images.githubusercontent.com/17886190/160184199-610f5357-6a88-43e4-95d8-b5114fc3fdf5.png"/>
</p>


- Interactive Flood Fill: Edge detection and object fill

<p align="center">
  <img style="width: 65%; height:65%;", src="https://user-images.githubusercontent.com/17886190/160184291-8ccf8d01-7319-4554-8879-09e13239be9c.png"/>
</p>

- AND Mask with original image

<p align="center">
  <img style="width: 65%; height:65%;", src="https://user-images.githubusercontent.com/17886190/160184403-e8ecb62e-adc1-43d9-9604-96e7c79b171d.png"/>
</p>


#### Feature extraction

It was used the color difference between objects. (not considered the white part of the image)
Diferent sizes between objects was penalized, however, it was used color and size descriptors.

#### Recognition and interpretation

It was implemented a simple KNN and used the calculation of the difference in color and size to classify the proximity of the tested image with the images in the database.

> k value equal to 5

#### Problems found

- Images in which the background is not white, in which the vegetables are on a towel, patterned surface, there are problems with extraction

- Images where one object touches another, causing the detected edge to see these distinct objects as one

- Images with different resolutions generate vegetables with different proportions, leading to the problem in calculating the difference in size

## Results

When predicting which vegetable it is, it renames the N images created in the segmentation with the name corresponding to that vegetable.

Displays the amount of vegetables of each type in a txt file.

The implementation of thresholding by color was tried, but without success.

The success rate for standardized images with a white background was: **98.978%**

One of the errors found in these images was a wrong comparison of a banana with an onion due to size differences in the analyzed image with the images stored in the database.

The second of the errors found was related to segmentation, in this case the banana due to its shape and inclination was captured.

