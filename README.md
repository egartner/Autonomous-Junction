# Autonomous Junction

## Objectives

The goal of this project is to create an autonomous junction using EV3 robots. We have at our disposal a track and the robots must be able to follow the road which is an 8-shaped black line and they should detect other robots in front of them to keep a safe distance.
The challenging part is to make the robots not collide at the junction of the track.

## Implementation

The solution implemented is centralized: the robots send messages to a server indicating their positions. The server use this information to calculate their relative distance to the junction and establish a passing order. From this list, the server then sends message to the robots one after the other: once a robot finished crossing the junction, it sends a message and the server know the next robot can move forward.
