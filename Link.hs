module Link ( Link, newL, linksL, connectsL, capacityL, delayL )
   where

import Point
import City
import Quality

data Link = Lin City City Quality deriving (Eq, Show)

newL :: City -> City -> Quality -> Link -- genera un link entre dos ciudades distintas
newL city1 city2 quality | nameC city1 == nameC city2 = error "Error de definicion del enlace: No es posible crear enlaces dentro de una misma ciudad."
                         | distanceC city1 city2 == 0 = error "Error de definicion del enlace: No es posible crear enlaces en un mismo punto."
                         | otherwise = Lin city1 city2 quality

connectsL :: City -> Link -> Bool   -- indica si esta ciudad es parte de este link
connectsL city (Lin city1L city2L _) = city == city1L || city == city2L

linksL :: City -> City -> Link -> Bool -- indica si estas dos ciudades distintas estan conectadas mediante este link
linksL city1 city2 link = connectsL city1 link && connectsL city2 link

getCity1L :: Link -> City
getCity1L (Lin city1 city2 _) = city1

getCity2L :: Link -> City
getCity2L (Lin city1 city2 _) = city2

capacityL :: Link -> Int
capacityL (Lin _ _ quality) = capacityQ quality

delayL :: Link -> Float     -- la demora que sufre una conexion en este canal
delayL (Lin city1L city2L quality) = (delayQ quality) / (distanceC city1L city2L) 