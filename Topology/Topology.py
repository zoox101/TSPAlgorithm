import random
import math
import copy

def generate_point(max=10):
    return (random.random()*max, random.random()*max)

def distance(point1, point2):
    dx = math.pow(point1[0] - point2[0], 2)
    dy = math.pow(point1[1] - point2[1], 2)
    return math.sqrt(dx + dy)

def path_length(path, points):
    length = 0
    for i in xrange(len(path)-1):
        dist = distance(points[path[i]], points[path[i+1]])
        length += dist
    length += distance(points[path[-1]], points[path[0]])
    return length

def get_closest(point, points, hits):
    valid_index = []
    for i in xrange(len(points)):
        if i != point and i not in hits:
            valid_index.append(i)
    return _get_closest(point, valid_index, points)

def _get_closest(point, valid_index, points):
    index = 0
    closest_point = points[valid_index[0]]
    point = points[point]
    for i in xrange(len(valid_index)):
        valid_point = points[valid_index[i]]
        if distance(point, valid_point) < distance(point, closest_point):
            index = i
            closest_point = valid_point
    return valid_index[index]

def get_second_closest(point, points, hits):
    closest = get_closest(point, points, hits)
    new_hits = copy.deepcopy(hits)
    new_hits.add(closest)
    if len(new_hits) == len(points):
        return closest
    else:
        return get_closest(point, points, new_hits)

def generate_path(points, heuristic=[]):
    path = [0]; hits = set(); hits.add(0)
    while len(path) < len(points):
        if path[-1] in heuristic:
            next = get_second_closest(path[-1], points, hits)
        else:
            next = get_closest(path[-1], points, hits)
        path.append(next); hits.add(next)
    return path


#Generating a random set of points
points = []
for x in xrange(20):
    points.append(generate_point(10))

paths = []
for i in xrange(len(points)):
    paths.append([])
    for j in xrange(len(points)):
        path = generate_path(points, [i,j])
        paths[i].append(path)

topology = []
for i in xrange(len(paths)):
    topology.append([])
    for j in xrange(len(paths[i])):
        path = paths[i][j]
        topology[i].append(path_length(path, points))
        print str(i) + ',' + str(j) + ',' + str(topology[i][j] - 50)
