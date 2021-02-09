# Tarea 1

### 1. Escribir una función en LISP que invierta una lista a profundidad sin usar mapcar.
Regresa la lista inversa, método no destructivo
```
(defun B (lst)
	(cond 
		((atom lst) lst)
		(t (append (B (cdr lst)) (B (car lst))))
		
	)
)
```


### 2. Escribir una función en LISP que sume una lista a profundidad usando mapcar.
Usamos variable auxiliar `x` y va sumando ahi. Regresa la lista sumada elemento a elemento
y el valor de `x` es la suma de todos. Método no destructivo. No maneja listas que 
tengan elementos que no sean números.

```
(setq x 0)
(defun D(lst)
	(cond 
		((listp lst) (mapcar #'D lst)) 
		(t (incf x lst))
	)
)
```

### 3. Escribir una función en LISP que sume una lista a profundidad sin usar mapcar.
Usa la misma estructura que la 1. Suma a profundidad sin usar mapcar, no usa variables 
auxiliares de ambiente. 
```
(defun C(lst)
	(cond 
		((null lst) 0)
		((atom lst) lst)
		(t (+ (C (cdr lst)) (C (car lst))))
	)
)
```
