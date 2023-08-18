module Tunel ( Tunel, newT, connectsT, usesT, delayT )
   where

import Point
import City
import Quality
import Link

data Tunel = Tun [Link] deriving (Eq, Show)

newT :: [Link] -> Tunel
newT links | links == [] = error "El tunel debe tener al menos un link."
           | foldr(\linkX acc -> (capacityL linkX <= 0) || acc) False links = error "Todos los enlaces deben tener capacidad disponiblle para crear el tunel"
           | otherwise = Tun links

esElInicio :: City -> [Link] -> Bool
esElInicio city links | checksLink (head links) (head(tail links)) == city = True
                      | otherwise = False

esElFinal :: City -> [Link] -> Bool
esElFinal city links | checksLink (last links) (head (tail (reverse links))) == city = True
                     | otherwise = False

checksLink:: Link -> Link -> City
checksLink link1 link2 | connectsL (getCity1L link1) link2 = getCity2L link1
                       | otherwise = getCity1L link1

connectsT :: City -> City -> Tunel -> Bool -- inidca si este tunel conceta estas dos ciudades distintas
connectsT city1 city2 (Tun links) | length links == 1 && linksL (head links) city1 city2 = True
                                  | esElInicio city1 && esElFinal city2 = True
                                  | esElInicio city2 && esElFinal city1 = True
                                  | otherwise = False


usesT :: Link -> Tunel -> Bool  -- indica si este tunel atraviesa ese link
usesT linkA (Tun links) = elem linkA links

delayT :: Tunel -> Float -- la demora que sufre una conexion en este tunel
delayT (Tun links) = foldr (\link acc -> (delayL link) + acc) 0  links