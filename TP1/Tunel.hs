module Tunel ( Tunel, newT, connectsT, usesT, delayT )
   where

import Point
import City
import Quality
import Link

data Tunel = Tun [Link] deriving (Eq, Show)

newT :: [Link] -> Tunel
newT linksUsed | linksUsed == [] = error "El tunel debe tener al menos un link."
               | (elem) 0 (map capacityL linksUsed) = error "Todos los enlaces deben tener capacidad disponible para crear el tunel."
               | otherwise = Tun linksUsed

esElInicio :: City -> [Link] -> Bool
esElInicio cityLinked links | connectsL cityLinked (head links) && not (connectsL cityLinked (head (tail links))) = True
                            | otherwise = False

esElFinal :: City -> [Link] -> Bool
esElFinal cityLinked links | connectsL cityLinked (last links) && not (connectsL cityLinked (head (tail (reverse links)))) = True
                           | otherwise = False

connectsT :: City -> City -> Tunel -> Bool -- inidca si este tunel conceta estas dos ciudades distintas
connectsT city1InT city2InT (Tun links) | length links == 1 && linksL city1InT city2InT (head links) = True
                                        | esElInicio city1InT links && esElFinal city2InT links = True
                                        | esElInicio city2InT links && esElFinal city1InT links = True
                                        | otherwise = False


usesT :: Link -> Tunel -> Bool  -- indica si este tunel atraviesa ese link
usesT linkA (Tun linksUsed) = (elem) linkA linksUsed

delayT :: Tunel -> Float -- la demora que sufre una conexion en este tunel
delayT (Tun links) = foldr (\iterativeLink accDelayT -> delayL iterativeLink + accDelayT) 0  links
