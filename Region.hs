module Region ( Region, newR, foundR, linkR, tunelR, pathR, linksForR, connectedR, linkedR, delayR, availableCapacityForR, usedCapacityForR )
   where

import Point
import City
import Quality
import Link
import Tunel

data Region = Reg [City] [Link] [Tunel] deriving (Eq, Show)

newR :: Region
newR = Reg [] [] []

isCInRegionR :: Region -> City -> Bool
isCInRegionR (Reg cities _ _) city = elem city cities

foundR :: Region -> City -> Region -- agrega una nueva ciudad a la región
foundR (Reg cities links tunels) city | elem city cities = error "La ciudad ya se encuentra en la region"
                                      | otherwise = Reg (city:cities) links tunels

linkR :: Region -> City -> City -> Quality -> Region -- enlaza dos ciudades de la región con un enlace de la calidad indicada
linkR (Reg cities links tunels) city1 city2 quality | not (isCInRegionR (Reg cities links tunels) city1) = error "Para establecer el enlace, ambas ciudades deben estar en la region."
                                                    | not (isCInRegionR (Reg cities links tunels) city2) = error "Para establecer el enlace, ambas ciudades deben estar en la region."
                                                    | elem (newL city1 city2 quality) links = error "Ya existe un link de estas caracteristicas en la region"
                                                    | otherwise =  Reg cities ((newL city1 city2 quality):links) tunels

tunelR :: Region -> [ City ] -> Region -- genera una comunicación entre dos ciudades distintas de la región
tunelR (Reg citiesR links tunels) cities | not (foldr (\linkX acc -> capacityL linkX - usedCapacityForR (Reg citiesR links tunels) linkX >= 1 && acc) True (reverse (pathR (Reg citiesR links tunels) cities []))) = error "La capacidad de los links no son suficientes."
                                         | otherwise = Reg citiesR links ((newT (reverse (pathR (Reg citiesR links tunels) cities []))): tunels)

connectedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan conectadas por un tunel
connectedR (Reg _ _ tunels) city1 city2 = foldr (\tunelX acc -> connectsT city1 city2 tunelX || acc) False tunels

linkedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan enlazadas
linkedR (Reg cities links tunels) city1 city2 | not (isCInRegionR (Reg cities links tunels) city1) = error "Las ciudades no pertenecen a la region."
                                              | not (isCInRegionR (Reg cities links tunels) city2) = error "Las ciudades no pertenecen a la region."
                                              | otherwise = foldr(\linkX acc -> linksL city1 city2 linkX || acc) False links 

delayR :: Region -> City -> City -> Float -- dadas dos ciudades conectadas, indica la demora
delayR (Reg _ _ tunels) city1 city2 = foldr (\tunelX acc -> if connectsT city1 city2 tunelX then acc + delayT tunelX else acc) 0 tunels

pathR :: Region -> [City] -> [Link] -> [Link]
pathR region cities linkpath | length cities == 2 = linksForR region (head cities) (head (tail cities)):linkpath
                             | otherwise = pathR region (tail cities) (linksForR region (head cities) (head (tail cities)):linkpath)

usedCapacityForR :: Region -> Link -> Int
usedCapacityForR (Reg _ _ tunels) link = length [tunel | tunel <- tunels, usesT link tunel]

linksForR :: Region -> City -> City -> Link
linksForR (Reg cities links tunels) city1 city2 | [link | link <- links, linksL city1 city2 link] == [] = error "No existe link en la region que enlace dichas ciudades."
                                                 | otherwise = head [link | link <- links, linksL city1 city2 link]

availableCapacityForR :: Region -> City -> City -> Int -- indica la capacidad disponible entre dos ciudades
availableCapacityForR (Reg cities links tunels) city1 city2 | not (isCInRegionR (Reg cities links tunels) city1) = error "Las ciudades no pertenecen a la region."
                                                            | not (isCInRegionR (Reg cities links tunels) city2) = error "Las ciudades no pertenecen a la region."
                                                            | otherwise = capacityL (linksForR (Reg cities links tunels) city1 city2) - usedCapacityForR (Reg cities links tunels) (linksForR (Reg cities links tunels) city1 city2)





x = newP 3 4
y = newP 1 3
z = newP 6 4
w = newP 9 5

c1 = newC "c1" x
c2 = newC "c2" y
c3 = newC "c3" z
c4 = newC "c4" w

reg = newR
reg1 = foundR reg c1
reg2 = foundR reg1 c2
reg3 = foundR reg2 c3
reg4 = foundR reg3 c4
qua = newQ "cobre" 5 7
reg5 = linkR reg4 c1 c2 qua
reg6 = linkR reg5 c2 c3 qua
reg7 = linkR reg6 c3 c4 qua