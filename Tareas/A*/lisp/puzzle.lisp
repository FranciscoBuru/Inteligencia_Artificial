; Lo usado no visto en clase fue sacado del libro "Practical Common Lisp"
; disponible en http://www.gigamonkeys.com/book/

;(load "puzzle.lisp" :print t)

;(id,  padre, nivel, costoM, costoH, mov, cero, (x1, x2, x31 ... , x9))
(setf lst (list 0 0 0 0 0 0 7(list 8 6 7 2 5 4 3 0 1)))

(defvar *cont* 0)
(defvar *lst* nil)

(defun agrega() (push '(100) *lst*)(incf *cont*))

;Usamos defvar pues nos permite definir variables que cambian

(defvar *open* nil)
(defvar *closed* nil)
(defvar final (list 1 2 3 4 5 6 7 8 0))
(defvar *nodeCounter* 0)
(setf *open* nil)
(setf *closed* nil)

;Forma de un nodo
;(id,  padre, nivel, costoM, costoH, mov, cero, (x1, x2, x31 ... , x9))

;Metodo para sacar distancia manhattan de un arreglo.
(defun costoM (lst)
	(apply '+ (mapcar #'(lambda (x) (manhattan lst x)) lst)))

;Metodo para sacar Manhattan de un punto
(defun manhattan (lst x)
	(setf pos (position x lst))
	(setf posF (position x final))
	(setf x1 (posx pos))
	(setf y1 (posx posf))
	(+ (abs (- x1 y1)) (abs (- (mod pos 3) (mod posF 3)))))

(defun posx (x)
	(cond
		((< (- x 3) 0) 0)
		((< (- x 6) 0) 1)
		(t 2)))

;Distancia de hamming
(defun costoh (lst)
	(apply '+ (mapcar #'(lambda (x) (hamming lst x)) lst))
)
(defun hamming (lst x)
	(if (= (position x lst) (position x final)) 0 1))


;Metodo que regresa nueva lista igual a la original, reverse profundo
(defun nueva  (lst)
	(tree-reverse (tree-reverse lst)))
	
;Funcion que valida un movimiento. 
(defun validamov (pos mov)
	(setf x (posx pos))
	(setf y (mod pos 3))
	(cond 
		((= mov 1) (if (= 0 x) nil t))
		((= mov 2) (if (= 2 y) nil t))
		((= mov 3) (if (= 2 x) nil t))
		((if (= 0 y) nil t))))
	
;Funcion que genera hijos
(defun generahijos (lst)
	(setf lst2 (nueva lst))
	(setf padre (first lst2))
	(setf nivel (+ (third lst2) 1))
	(setf cero (seventh lst2))
	(setf cerox (posx cero))
	(setf ceroy (mod cero 3))
	(mapcar #'(lambda (x) (hijo lst2 x padre nivel cero cerox ceroy)) (list 1 2 3 4)))

(defun hijo (lst mov padre nivel cero cerox ceroy)
	(if (validamov cero mov) (creahijo lst mov padre nivel cero cerox ceroy) 0))

;(id,  padre, nivel, costoM, costoH, mov, cero, (x1, x2, x31 ... , x9))
(defun creahijo (lst mov padre nivel cero cerox ceroy)
	(setf hijo (nueva lst))
	(setf (car hijo) (+ *nodeCounter* 1))
	(incf *nodeCounter*)
	(setf (second hijo) padre)
	(setf (third hijo) nivel)
	(cond 
		((= mov 2) (rotatef (nth cero (car (last hijo))) (nth (+ cero 1) (car (last hijo))))(setf (seventh hijo) (+ cero 1)))
		((= mov 3) (rotatef (nth cero (car (last hijo))) (nth (+ cero 3) (car (last hijo))))(setf (seventh hijo) (+ cero 3)))
		((= mov 4) (rotatef (nth cero (car (last hijo))) (nth (- cero 1) (car (last hijo))))(setf (seventh hijo) (- cero 1)))
		(t (rotatef (nth cero (car (last hijo))) (nth (- cero 3) (car (last hijo))))(setf (seventh hijo) (- cero 3))))
	(setf (fourth hijo) (max (fourth lst) (+ nivel (costoM (car (last hijo))))))
	;(setf (fifth hijo) (max (fifth lst) (+ nivel (costoH (car (last hijo))))))
	(setf (sixth hijo) mov)
	(cond 
		((some #'ist (mapcar #'(lambda (x) (equal (car (last hijo)) (car (last x)))) *closed* )))
		;((some #'ist (mapcar #'(lambda (x) (if (equal (car (last hijo)) (car (last x))) (discrimina hijo x) 0)) *open* )))
		(t (push  hijo *open*))))

(defun ist (a)
	(eql a t))

(defun discrimina (hijo otro)
	(cond 
		((= (fourth hijo) (fourth otro)) 
		(cond 
			((>= (fifth hijo) (fifth otro)) 1)
			(t (setf (fifth otro) (+ (fifth hijo) 0)) 1)))
		((< (fourth hijo) (fourth otro)) (setf (fourth otro) (+ (fourth hijo) 0))(setf (fifth otro) (+ (fifth hijo) 0)) 1)
		(t 1)))
		
(defun compara-costo (a b)
	(< (fourth a) (fourth b)))
	
;(defun compara-costo (a b)
;	(and (< (fourth a) (fourth b)) (< (fifth a) (fifth b))))
		
		
(defun estrella (inicio)
	(push inicio *open*)
	(dotimes (n 1000000)
		(sort *open* 'compara-costo)
		(if (> (length *open*) 500) (setf *open* (subseq *open* 0 200)) 0)
		(setf actual (car *open*))
		(push (pop *open*) *closed*)
		(if (equal (car (last actual)) final) (return t) 0)
		(generaHijos actual)))

(defun tree-reverse(tree)
			(if (listp tree) (mapcar #'tree-reverse (reverse tree)) tree))

(estrella lst)