import Point
import City
import Quality
import Link
import Tunel
import Region

x = newP 2 3
y = newP 4 1
z = newP 2 3
w = newP 5 7
v = newP 8 2

tPoint = [x == z,
          difP x x == 0]

c1 = newC "ciudad1" x
c2 = newC "ciudad2" x
c3 = newC "ciudad3" y
c4 = newC "ciudad1" y
c5 = newC "ciudad1" x
c6 = newC "ciudad6" w
c7 = newC "ciudad7" v

tCity = [c1 == c5,
         c1 /= c2,
         c1 /= c4,
         nameC c1 == "ciudad1",
         distanceC c1 c1 == 0,
         distanceC c1 c2 == 0]

-- Tira el error de capacidad negativa en una calidad
errorCalidadNegativa = newQ "cobre" (-3) 42

-- Tira el error de delay cero en una calidad
errorDelayCero = newQ "cobre" 3 0

q1 = newQ "cobre" 3 42
q2 = newQ "cobre" 3 (-42)
q3 = newQ "cobre" 0 22


tQuality = [q2 == q1,
            capacityQ q1 == 3,
            capacityQ q3 == 0,
            delayQ q1 == 42]

-- Tira el error de link entre dos ciudades con mismo nombre
errorLinkEntreMismaCiudad = newL c1 c1 q1

-- Tira el error de link entre dos ciudades con mismo punto
errorLinkEntreMismoPunto = newL c1 c2 q1

l1 = newL c1 c3 q1
l2 = newL c1 c3 q2
l3 = newL c2 c4 q3
l4 = newL c1 c6 q1
l5 = newL c3 c6 q1
tLink = [l1 == l2,
         l4 /= l5,
         l4 /= l1,
         connectsL c1 l1,
         connectsL c3 l1, 
         not (linksL c1 c2 l1),
         linksL c1 c3 l1]

-- Tira el error de tunel con lista de links vacia
errorTunelVacio = newT []

-- Tira el error de tunel con link sin capacidad
errorLinkSinCapacidad = newT [l3]

t1 = newT [l1]
t2 = newT [l1, l5]

tTunel = [connectsT c1 c3 t1,
          connectsT c3 c1 t1,
          connectsT c1 c6 t2,
          connectsT c6 c1 t2,
          not (connectsT c3 c6 t2),
          usesT l1 t1,
          usesT l2 t1,
          not (usesT l4 t2),
          delayT t1 == delayL l1,
          delayT t2 == delayL l1 + delayL l5]

r1 = newR
r2 = foundR r1 c1
r3 = foundR r2 c6
r4 = linkR r3 c1 c6 q1
r5 = foundR r4 c3
r6 = linkR r5 c1 c3 q3
r7 = linkR r5 c1 c3 q1
r8 = tunelR r7 [c6,c1,c3]
r9 = tunelR r8 [c1,c3]
r10 = foundR r9 c7
r11 = linkR r10 c1 c7 q3

-- Tira el error de ciuadad ya perteneciente en la region
errorCiudadYaEnRegion = foundR r2 c1

-- Tira el error de enlace entre ciudades no pertenecientes a la region
errorCiudadNoEnRegion = linkR r2 c1 c3 q1
errorCiudadNoEnRegion2 = linkR r2 c3 c1 q1
errorCiudadNoEnRegion3 = linkedR r8 c4 c3
errorCiudadNoEnRegion4 = linkedR r8 c3 c4
errorCiudadNoEnRegion5 = availableCapacityForR r8 c3 c4
errorCiudadNoEnRegion6 = availableCapacityForR r8 c4 c3

-- Tira el error de link ya existente en la region
errorLinkYaEnRegion = linkR r4 c1 c6 q1

-- Tira el error de link sin capacidad para tunel
errorLinkSinCapacidad2 = tunelR r6 [c1,c3]

tRegion = [connectedR r8 c6 c3,
           connectedR r9 c3 c6,
           not (connectedR r8 c1 c6),
           connectedR r11 c3 c1,
           linkedR r9 c1 c3,
           linkedR r9 c3 c1,
           not (linkedR r9 c3 c6),
           delayR r8 c3 c6 == delayL l4 + delayL l1,
           availableCapacityForR r11 c1 c3 == 1,
           availableCapacityForR r11 c3 c1 == 1,
           availableCapacityForR r11 c1 c7 == 0]
