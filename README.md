# Twitch to Hue

An application to visualise follows and hosts with a Phillips Hue controller and bulb, via Streamlabs.

**WARNING** This is very rough and ready, it's not user friendly at this point at all.

## Building

Build it with maven.

```mvn package```

Output will be in ```target/```, move it to where ever.

## Configuring

Create a file in same directory as the jar called ```twitchtohue.properties```.

```
hue.address=IP-ADDRESS
hue.token=HUE_TOKEN
hue.lightId=18
hue.onTime=6000
http.port=22222
```

### IP Address

Change the ```IP-ADDRESS``` to that of your Hue controllers address, exercise for the reader to identify that,
it's too complicated to have written here.

### Hue Token

To generate a ```HUE_TOKEN```, connect to ```http://IP-ADDRESS/debug/clip.html```. 

In the URL box, replace the content with just ```/api```.

In the Message Body, put ```{"devicetype":"twitchtohue"}```. 

Press the Button on your Hue controller, then click ```POST```.

In the response you should see a ```"username": "HUE_TOKEN""```, grab the token without the ```"```.

### Light ID

In CLIP, change the URL to ```/api/HUE_TOKEN/lights``` (replacing the HUE_TOKEN with the one you just found out).

Clear the Message Body, and click ```GET```.

Assuming you have named your bulbs (what kind of a nerd are you after all?), find the name of the bulb you want, then refer to it's ID number which will be at the start of that bulbs section.

### Streamlabs

Go to your alert box in the dashboard, click the Follows section, find Enable Custom HTML/CSS, scroll to the bottom of the code and insert:

```<img src="http://localhost:22222/follow/{name}" style="visibility: hidden" />``` 

Go the same for Hosting, use this HTML:

```<img src="http://localhost:22222/host/{name}" style="visibility: hidden" />```

## Running

Start twitchtohue before you stream with:

```java -jar twitchtohue-0.0.1-SNAPSHOT.jar```

Enjoy.