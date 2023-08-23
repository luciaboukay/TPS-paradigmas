import Point
import City
import Quality
import Link
import Tunel
import Region

x = newP 2 3
y = newP 4 1
tPoint = [difP x x == 0]

c1 = newC "ciudad1" x
c2 = newC "ciudad2" x

c3 = newC "ciudad3" y
tCity = [nameC c1 == "ciudad1", distanceC c1 c1 == 0]

-- Tira el error de capacidad negativa en una calidad
error1 = newQ "cobre" (-3) 42

q1 = newQ "cobre" 4 42
tQuality = [newQ "cobre" 4 (-42) == q1,
            capacityQ q1 == 4,
            delayQ q1 == 42]

-- Tira el error de link entre dos ciudades con mismo nombre
error2 = newL c1 c1 q1

-- Tira el error de link entre dos ciudades con mismo punto
error3 = newL c1 c2 q1

l1 = newL c1 c3 q1
tLink = [connectsL c1 l1,
         connectsL c3 l1]
