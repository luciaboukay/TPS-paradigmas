module Region ( Region, newR, foundR, linkR, tunelR, pathR, linksForR, connectedR, linkedR, delayR, availableCapacityForR, usedCapacityForR )
   where

import Point
import City
import Quality
import Link
import Tunel

data Region = Reg [City] [Link] [Tunel]

newR :: Region
newR = Reg [] [] []

isCInRegionR :: Region -> City -> Bool
isCInRegionR (Reg cities _ _) city = elem city cities

foundR :: Region -> City -> Region -- agrega una nueva ciudad a la región
foundR (Reg cities links tunels) city = Reg (city:cities) links tunels

linkR :: Region -> City -> City -> Quality -> Region -- enlaza dos ciudades de la región con un enlace de la calidad indicada
linkR (Reg cities links tunels) city1 city2 quality | not (isCInRegionR (Reg cities links tunels) city1) = error "Para establecer el enlace, ambas ciudades deben estar en la region."
                                                    | not (isCInRegionR (Reg cities links tunels) city2) = error "Para establecer el enlace, ambas ciudades deben estar en la region."
                                                    | otherwise =  Reg cities ((newL city1 city2 quality):links) tunels

tunelR :: Region -> [ City ] -> Region -- genera una comunicación entre dos ciudades distintas de la región
tunelR (Reg citiesR links tunels) cities | not (foldr (\linkX acc -> capacityL linkX - usedCapacityForR (Reg citiesR links tunels) linkX >= 1 && acc) True (pathR (Reg citiesR links tunels) cities [])) = error "La capacidad de los links no son suficientes."
                                         | otherwise = Reg citiesR links ((newT (pathR (Reg citiesR links tunels) cities [])): tunels)

connectedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan conectadas por un tunel
connectedR (Reg _ _ tunels) city1 city2 = foldr (\tunelX acc -> connectsT city1 city2 tunelX || acc) False tunels

linkedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan enlazadas
linkedR (Reg cities links tunels) city1 city2 | not (isCInRegionR (Reg cities links tunels) city1) = error "Las ciudades no pertenecen a la region."
                                              | not (isCInRegionR (Reg cities links tunels) city2) = error "Las ciudades no pertenecen a la region."
                                              | otherwise = foldr(\linkX acc -> linksL city1 city2 linkX || acc) False links 

delayR :: Region -> City -> City -> Float -- dadas dos ciudades conectadas, indica la demora
delayR (Reg _ _ tunels) city1 city2 = foldr (\tunelX acc -> if connectsT city1 city2 tunelX then acc + delayT tunelX else acc) 0 tunels

pathR :: Region -> [City] -> [Link] -> [Link]
pathR region cities linkpath | length cities == 2 = [linksForR region (head cities) (head (tail cities))]
                                  | otherwise = pathR region (tail cities) linkpath ++ linkpath

usedCapacityForR :: Region -> Link -> Int
usedCapacityForR (Reg _ _ tunels) link = length [tunel | tunel <- tunels, usesT link tunel]

linksForR :: Region -> City -> City -> Link
linksForR (Reg cities links tunels) city1 city2 | [link | link <- links, linksL city1 city2 link] == [] = error "No existe link en la region que enlace dichas ciudades."
                                                 | otherwise = head [link | link <- links, linksL city1 city2 link]

availableCapacityForR :: Region -> City -> City -> Int -- indica la capacidad disponible entre dos ciudades
availableCapacityForR (Reg cities links tunels) city1 city2 | not (isCInRegionR (Reg cities links tunels) city1) = error "Las ciudades no pertenecen a la region."
                                                            | not (isCInRegionR (Reg cities links tunels) city2) = error "Las ciudades no pertenecen a la region."
                                                            | otherwise = capacityL (linksForR (Reg cities links tunels) city1 city2) - usedCapacityForR (Reg cities links tunels) (linksForR (Reg cities links tunels) city1 city2)

