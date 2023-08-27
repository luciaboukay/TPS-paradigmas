module Region ( Region, newR, foundR, linkR, tunelR, connectedR, linkedR, delayR, availableCapacityForR )
   where

import Point
import City
import Quality
import Link
import Tunel

data Region = Reg [City] [Link] [Tunel] deriving (Eq)

instance Show Region
      where show (Reg cities links tunels) = "Cities: " ++ show cities ++ "\nLinks: " ++ show links ++ "\nTunels: " ++ show tunels

newR :: Region
newR = Reg [] [] []

isCInRegionR :: Region -> City -> Bool
isCInRegionR (Reg citiesInR _ _) city = elem city citiesInR

foundR :: Region -> City -> Region -- agrega una nueva ciudad a la región
foundR (Reg citiesInR linksInR tunelsInR) city | isCInRegionR (Reg citiesInR linksInR tunelsInR) city = error "La ciudad ya se encuentra en la region."
                                               | otherwise = Reg (city:citiesInR) linksInR tunelsInR

linkR :: Region -> City -> City -> Quality -> Region -- enlaza dos ciudades de la región con un enlace de la calidad indicada
linkR (Reg citiesInR linksInR tunelsInR) city1 city2 quality | not (isCInRegionR (Reg citiesInR linksInR tunelsInR) city1) = error "Para establecer el enlace, ambas ciudades deben estar en la region."
                                                             | not (isCInRegionR (Reg citiesInR linksInR tunelsInR) city2) = error "Para establecer el enlace, ambas ciudades deben estar en la region."
                                                             | elem (newL city1 city2 quality) linksInR = error "Ya existe un link de estas caracteristicas en la region."
                                                             | otherwise =  Reg citiesInR ((newL city1 city2 quality):linksInR) tunelsInR

tunelR :: Region -> [ City ] -> Region -- genera una comunicación entre dos ciudades distintas de la región
tunelR (Reg citiesInR linksInR tunelsInR) cities | length (pathR (Reg citiesInR linksInR tunelsInR) cities []) < (length cities)-1 = error "La capacidad de los links no son suficientes."
                                                 | otherwise = Reg citiesInR linksInR ((newT (pathR (Reg citiesInR linksInR tunelsInR) cities [])): tunelsInR)

connectedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan conectadas por un tunel
connectedR (Reg citiesInR linksInR tunelsInR) city1 city2 | not (isCInRegionR (Reg citiesInR linksInR tunelsInR) city1) = error "Las ciudades no pertenecen a la region."
                                                          | not (isCInRegionR (Reg citiesInR linksInR tunelsInR) city2) = error "Las ciudades no pertenecen a la region."
                                                          | otherwise = (elem) True (map (connectsT city1 city2) tunelsInR)

linkedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan enlazadas
linkedR (Reg citiesInR linksInR tunelsInR) city1 city2 | not (isCInRegionR (Reg citiesInR linksInR tunelsInR) city1) = error "Las ciudades no pertenecen a la region."
                                                       | not (isCInRegionR (Reg citiesInR linksInR tunelsInR) city2) = error "Las ciudades no pertenecen a la region."
                                                       | otherwise = (elem) True (map (linksL city1 city2) linksInR)

delayR :: Region -> City -> City -> Float -- dadas dos ciudades conectadas, indica la demora
delayR (Reg _ _ tunels) city1 city2 = foldr (\iterativeTunel delay -> if connectsT city1 city2 iterativeTunel then delay + delayT iterativeTunel else delay) 0 tunels

pathR :: Region -> [City] -> [Link] -> [Link]
pathR region [city1, city2] linkPath = if availableCapacityForR region city1 city2 >= 1 then linkPath ++ [linksForR region city1 city2] 
                                          else linkPath
pathR region citiesInR linkPath = if availableCapacityForR region (head citiesInR) (head (tail citiesInR)) >= 1 then pathR region (tail citiesInR) (linkPath ++ [linksForR region (head citiesInR) (head (tail citiesInR))]) 
                                      else pathR region (tail citiesInR) linkPath

usedCapacityForR :: Region -> Link -> Int
usedCapacityForR (Reg _ _ tunels) link = length [tunel | tunel <- tunels, usesT link tunel]

linksForR :: Region -> City -> City -> Link
linksForR (Reg _ linksInR _) city1 city2 | [link | link <- linksInR, linksL city1 city2 link] == [] = error "No existe link en la region que enlace dichas ciudades."
                                         | otherwise = head [link | link <- linksInR, linksL city1 city2 link]

availableCapacityForR :: Region -> City -> City -> Int -- indica la capacidad disponible entre dos ciudades
availableCapacityForR region city1 city2 | not (isCInRegionR region city1) = error "Las ciudades no pertenecen a la region."
                                         | not (isCInRegionR region city2) = error "Las ciudades no pertenecen a la region."
                                         | otherwise = capacityL (linksForR region city1 city2) - usedCapacityForR region (linksForR region city1 city2)


x = newP 2 3
y = newP 4 1
w = newP 5 7
v = newP 8 2

c1 = newC "ciudad1" x
c3 = newC "ciudad3" y
c6 = newC "ciudad6" w
c7 = newC "ciudad7" v

q1 = newQ "cobre" 3 42
q3 = newQ "cobre" 0 22

r1 = newR
r2 = foundR r1 c1
r3 = foundR r2 c6
r4 = linkR r3 c1 c6 q1
r5 = foundR r4 c3
r7 = linkR r5 c1 c3 q1
r8 = tunelR r7 [c6,c1,c3]
r9 = tunelR r8 [c1,c3]
r10 = foundR r9 c7
r11 = linkR r10 c1 c7 q3

-- Tira el error de link no existente en la region
errorLinkNoEnRegion = linksForR r11 c3 c7

tFuncionesParciales = [length (pathR r11 [c6,c1,c7] []) == 1,
                       length (pathR r11 [c6,c1] []) == 1]