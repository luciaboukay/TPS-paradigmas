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

foundR :: Region -> City -> Region -- agrega una nueva ciudad a la región
foundR (Reg cities _ _) city = city:cities

linkR :: Region -> City -> City -> Quality -> Region -- enlaza dos ciudades de la región con un enlace de la calidad indicada
linkR (Reg cities links _) city1 city2 quality | not (((elem) city1 cities) && ((elem) city2 cities)) = error "Para establecer el enlace, ambas ciudades deben estar en la region."
                                               | otherwise = (newL city1 city2 quality):links

tunelR :: Region -> [ City ] -> Region -- genera una comunicación entre dos ciudades distintas de la región
tunelR (Reg citiesR links _) cities | foldr(\linkX acc -> linksL (first cities) (last cities) linkX)

connectedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan conectadas por un tunel
connectedR (Reg _ _ tunels) city1 city2 = foldr (\tunelX acc -> connectsT city1 city2 tunelX || acc) False tunels

linkedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan enlazadas
linkedR (Reg cities links _) city1 city2 | not (elem city1 cities && elem city2 cities) = error "Las ciudades no pertenecen a la region."
                                         | otherwise = foldr(\linkX acc -> linksL city1 city2 linkX || acc) False links 

delayR :: Region -> City -> City -> Float -- dadas dos ciudades conectadas, indica la demora
delayR (Reg _ _ tunels) city1 city2 = foldr (\tunelX acc -> if connectsT city1 city2 tunelX then acc + delayT tunelX) 0 tunels

availableCapacityForR :: Region -> City -> City -> Int -- indica la capacidad disponible entre dos ciudades


