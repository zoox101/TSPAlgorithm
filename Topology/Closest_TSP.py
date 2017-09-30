import random
import math

def generate_point(max=10):
    return (random.random()*max, random.random()*max)

def distance(point1, point2):
    dx = math.pow(point1[0] - point2[0], 2)
    dy = math.pow(point1[1] - point2[1], 2)
    return math.sqrt(dx + dy)

points = []
for x in xrange(10):
    points.append(generate_point(10))

mapping = {}
for index in xrange(len(points)):
    point = points[index]
    tuple_list = []
    for i in xrange(len(points)):
        tuple_list.append((i, distance(point, points[i])))
        tuple_list = sorted(tuple_list, key=lambda x: x[1])
    mapping[index] = []
    for i in range(1, len(tuple_list)):
        mapping[index].append(tuple_list[i][0])

for key in mapping:
    print str(key) + ': ' + str(mapping[key])
