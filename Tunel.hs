module Tunel ( Tunel, newT, connectsT, usesT, delayT )
   where

import Point (Point, newP, difP)
import City (City, newC, nameC, distanceC)
import Quality (Quality, delayQ, capacityQ, newQ)
import Link (Link, newL, linksL, connectsL, capacityL, delayL)

data Tunel = Tun [Link] deriving (Eq, Show)

newT :: [Link] -> Tunel
newT links = Tun links --se podria sacar el parametro de ambos lados

first :: [Link] -> Link
first (firstLink:links) = firstLink

connectsT :: City -> City -> Tunel -> Bool -- inidca si este tunel conceta estas dos ciudades distintas
connectsT city1 city2 (Tun links) = 

usesT :: Link -> Tunel -> Bool  -- indica si este tunel atraviesa ese link
usesT linkA (Tun links) = elem linkA links

delayT :: Tunel -> Float -- la demora que sufre una conexion en este tunel
delayT (Tun links) = foldr (\link acc -> (delayL link) + acc) 0  links