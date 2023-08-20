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
esElInicio city links | connectsL city (head links) && not (connectsL city (head (tail links))) = True
                      | otherwise = False

esElFinal :: City -> [Link] -> Bool
esElFinal city links | connectsL city (head (reverse links)) && not (connectsL city (head (tail (reverse links)))) = True
                     | otherwise = False

connectsT :: City -> City -> Tunel -> Bool -- inidca si este tunel conceta estas dos ciudades distintas
connectsT city1 city2 (Tun links) | length links == 1 && linksL city1 city2 (head links) = True
                                  | esElInicio city1 links && esElFinal city2 links = True
                                  | esElInicio city2 links && esElFinal city1 links = True
                                  | otherwise = False


usesT :: Link -> Tunel -> Bool  -- indica si este tunel atraviesa ese link
usesT linkA (Tun links) = (elem) linkA links

delayT :: Tunel -> Float -- la demora que sufre una conexion en este tunel
delayT (Tun links) = foldr (\linkX acc -> (delayL linkX) + acc) 0  links