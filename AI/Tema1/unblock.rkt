#lang racket/gui

;; Tudor Berariu, 2014
;; Tema 1, Inteligență Artificială

(require test-engine/racket-tests)

;; http://www.quickflashgames.com/games/unblock/
;; http://www.quickflashgames.com/games/unblock-2/

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------
;; Define directions for blocks
(define VERTICAL 2048)
(define HORIZONTAL 2049)
;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

;; ----------------------------------------------------------------------------
(define cnt 0)
;; ----------------------------------------------------------------------------

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------
;; Datatype for blocks
(struct block
  (name orientation length)
  #:guard
  (λ (name orientation length type-name)
    (cond ((not (string? name))
           (error (~a "Bad value for name: " name)))
          ((not (member orientation `(,VERTICAL ,HORIZONTAL)))
           (error (~a "Bad value for orientation: " orientation)))
          ((not (and (number? length) (positive? length)))
           (error (~a "Bad value for length: " length)))
          (else (values name orientation length))
          ))                
  #:transparent)
;; ----------------------------------------------------------------------------
(define is-horizontal? (λ (blk) (= (block-orientation blk) HORIZONTAL)))
(define is-vertical? (λ (blk) (= (block-orientation blk) VERTICAL)))
;; ----------------------------------------------------------------------------
(check-expect (block "b01" VERTICAL 3) (block "b01" VERTICAL 3))
(check-error (block "b01" VERTICAL 0))
(check-expect (is-horizontal? (block "b01" HORIZONTAL 3)) #t)
;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------
;;; Datatype for placed blocks
(struct block-on-board block
  (row column)
  #:guard
  (λ (name orientation length row column type-name)
    (cond ((not (and (integer? row) (>= row 0)))
           (error (~a "Bad value for row (integer >=0 expected): " row)))
          ((not (and (integer? column) (>= column 0)))
           (error (~a "Bad value for column (integer >=0 expected): " column)))
          (else (values name orientation length row column))
          ))
  #:property prop:procedure
  (λ (self delta-rows delta-columns)
    (cond
      ((not (integer? delta-rows))
       (error (~a "Bad value for delta-rows: " delta-rows)))
      ((not (integer? delta-columns))
       (error (~a "Bad value for delta-columns " delta-columns)))
      ((and (is-horizontal? self)  (not (zero? delta-rows)))
       (error (~a "Cannot change the row of a HORIZONTAL block")))
      ((and (is-vertical? self)  (not (zero? delta-columns)))
       (error (~a "Cannot change the column of a VERTICAL block")))
      (else
       (struct-copy block-on-board self
                    [row (+ (block-on-board-row self) delta-rows)]
                    [column (+ (block-on-board-column self) delta-columns)]))
      ))
  #:methods
  gen:equal+hash
  [(define equal-proc
     (λ (b1 b2 equal?-recur)
       (and (equal?-recur (block-name b1) (block-name b2))
            (equal?-recur (block-orientation b1) (block-orientation b2))
            (equal?-recur (block-length b1) (block-length b2))
            (equal?-recur (block-on-board-row b1) (block-on-board-row b2))
            (equal?-recur (block-on-board-column b1) (block-on-board-column b2))
            )))
   (define hash-proc
     (λ (b hash-recur)
       (equal-hash-code (~a (block-name b) (block-orientation b) (block-length b)
                            (block-on-board-row b) (block-on-board-column b)))))
   (define hash2-proc
     (λ (b hash2-recur)
       (apply + (map hash2-recur (vector->list (struct->vector b))))))]
  #:transparent)
;; ----------------------------------------------------------------------------
(check-expect (block? (block-on-board "block2" VERTICAL 2 2 2)) #t)
(check-error (block-on-board "block2" VERTICAL 0 2 2))
(check-error (block-on-board "block2" VERTICAL 2 2 -1))
(check-expect (equal? ((block-on-board "block3" VERTICAL 2 2 2) -2 0)
                      (block-on-board "block3" VERTICAL 2 0 2)) #t)
;; ----------------------------------------------------------------------------
;; Check that two blocks do not overlap
(define blocks-ok?
  (λ (b1 b2)
    (cond ((equal? (block-name b1) (block-name b2)) #t)
          ((and (is-horizontal? b1) (is-horizontal? b2))
           (or (not (= (block-on-board-row b1) (block-on-board-row b2)))
               (< (+ (block-on-board-column b1) (block-length b1) -1) 
                  (block-on-board-column b2))
               (< (+ (block-on-board-column b2) (block-length b2) -1) 
                  (block-on-board-column b1))))
          ((and (is-vertical? b1) (is-vertical? b2))
           (or (not (= (block-on-board-column b1) (block-on-board-column b2)))
               (< (+ (block-on-board-row b1) (block-length b1) -1) 
                  (block-on-board-row b2))
               (< (+ (block-on-board-row b2) (block-length b2) -1) 
                  (block-on-board-row b1))))
          ((and (is-vertical? b1) (is-horizontal? b2))
           (or (< (+ (block-on-board-row b1) (block-length b1) -1) 
                  (block-on-board-row b2))
               (> (block-on-board-row b1) (block-on-board-row b2))
               (< (block-on-board-column b1) (block-on-board-column b2))
               (> (block-on-board-column b1) 
                  (+ (block-on-board-column b2) (block-length b2) -1))))
          ((and (is-horizontal? b1) (is-vertical? b2))
           (or (< (+ (block-on-board-row b2) (block-length b2) -1) 
                  (block-on-board-row b1))
               (> (block-on-board-row b2) (block-on-board-row b1))
               (< (block-on-board-column b2) (block-on-board-column b1))
               (> (block-on-board-column b2) 
                  (+ (block-on-board-column b1) (block-length b1) -1))))
          )))
;; ----------------------------------------------------------------------------
(check-expect (blocks-ok? (block-on-board "b1" HORIZONTAL 3 1 1)
                          (block-on-board "b2" HORIZONTAL 5 1 3)) #f)
(check-expect (blocks-ok? (block-on-board "b1" HORIZONTAL 3 1 1)
                          (block-on-board "b2" HORIZONTAL 5 2 3)) #t)
(check-expect (blocks-ok? (block-on-board "b1" VERTICAL 3 1 2)
                          (block-on-board "b2" HORIZONTAL 5 2 2)) #f)
(check-expect (blocks-ok? (block-on-board "b1" HORIZONTAL 3 3 8)
                          (block-on-board "b2" HORIZONTAL 5 2 3)) #t)
(check-expect (blocks-ok? (block-on-board "b1" VERTICAL 3 3 9)
                          (block-on-board "b2" HORIZONTAL 5 5 5)) #f)
(check-expect (blocks-ok? (block-on-board "b1" VERTICAL 7 3 3)
                          (block-on-board "b2" HORIZONTAL 3 5 0)) #t)
(check-expect (blocks-ok? (block-on-board "b1" VERTICAL 7 3 3)
                          (block-on-board "b2" HORIZONTAL 3 5 1)) #f)
(check-expect (blocks-ok? (block-on-board "b1" VERTICAL 7 3 3)
                          (block-on-board "b2" HORIZONTAL 3 10 1)) #t)
(check-expect (blocks-ok? (block-on-board "b1" HORIZONTAL 7 3 3)
                          (block-on-board "b2" HORIZONTAL 3 3 1)) #f)
;;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------
;;; Datatype for board
(struct board
  (height width blocks)
  #:guard
  (λ (height width blocks type-name)
    (cond
      ((not (and (integer? height) (positive? height)))
       (error (~a "Wrong value for board's height: " height)))
      ((not (and (integer? width) (positive? width)))
       (error (~a "Wrong value for board's width " width)))
      ((not (hash-has-key? blocks "red"))
       (error (~a "Red block is missing.")))
      (else (values height width blocks))
      ))
  #:property prop:procedure
  (λ (self blk-name delta-rows delta-columns)
    (if (not (hash-has-key? (board-blocks self) blk-name))
        (error (~a "Block " blk-name " not on board!"))
        (let ([blk ((hash-ref (board-blocks self) blk-name)
                    delta-rows delta-columns)])
          (cond
            ((and (is-horizontal? blk)
                  (> (+ (block-length blk) (block-on-board-column blk)) 
                     (board-width self)))
             (error (~a "Block moved outside the edges of the board.")))
            ((and (is-vertical? blk)
                  (> (+ (block-length blk) (block-on-board-row blk)) 
                     (board-height self)))
             (error (~a "Block moved outside the edges of the board.")))
            (else
             (struct-copy board self
                          [blocks (hash-set (board-blocks self) blk-name blk)]))
            ))
        ))
  #:methods
  gen:equal+hash
  [(define equal-proc
     (λ (b1 b2 equal?-recur)
       (and (= (board-width b1) (board-width b2))
            (= (board-height b1) (board-height b2))
            (equal?-recur (board-blocks b1) (board-blocks b2)))
       ))
   (define hash-proc
     (λ (b hash-recur)
       (+ (* 100 (equal-hash-code (board-blocks b)))
          (* 10 (board-width b))
          (board-height b))
       ))
   (define hash2-proc
     (λ (b hash2-recur) 
       (+ (hash2-recur (board-blocks b))
          (* (board-width b) 17)
          (* (board-height b) 7))
       ))]
  #:transparent)
;;; ----------------------------------------------------------------------------
(check-expect
 (board? (board 20 30 (hash "red" (block-on-board "red" HORIZONTAL 2 2 2))))
 #t)
(check-expect
 (equal? ((board 20 30 (hash "red" (block-on-board "red" HORIZONTAL 2 2 2)))
          "red" 0 13)
         (board 20 30 (hash "red" (block-on-board "red" HORIZONTAL 2 2 15))))
 #t)
(check-expect
 (equal? (board 6 7 (hash "red" (block-on-board "red" HORIZONTAL 3 3 3)
                          "b01" (block-on-board "b01" HORIZONTAL 3 1 1)
                          "b02" (block-on-board "b02" VERTICAL   2 1 6)
                          "b03" (block-on-board "b03" HORIZONTAL 2 2 2)
                          "b04" (block-on-board "b04" VERTICAL   2 2 1)
                          ))
         ((board 6 7 (hash "b04" (block-on-board "b04" VERTICAL   2 4 1)
                           "red" (block-on-board "red" HORIZONTAL 3 3 3)
                           "b03" (block-on-board "b03" HORIZONTAL 2 2 2)
                           "b01" (block-on-board "b01" HORIZONTAL 3 1 1)
                           "b02" (block-on-board "b02" VERTICAL   2 1 6)
                           ))
          "b04" -2 0))
 #t)
;;; ----------------------------------------------------------------------------
;;; Check if a board reached final state
(define is-final?
  (λ (b)
    (let* ([red (hash-ref (board-blocks b) "red")])
      (if (is-horizontal? red)
          (= (+ (block-on-board-column red) (block-length red)) (board-width b))
          (= (+ (block-on-board-row red) (block-length red)) (board-height b))))
    ))
;;; ----------------------------------------------------------------------------
(check-expect
 (is-final? (board 10 10 (hash "red" (block-on-board "red" HORIZONTAL 8 2 2))))
 #t)
(check-expect
 (is-final? (board 5 5 (hash "red" (block-on-board "red" VERTICAL 4 0 2))))
 #f)
(check-expect
 (is-final? ((board 5 5 (hash "red" (block-on-board "red" VERTICAL 4 0 2)))
             "red" 1 0))
 #t)
;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------

;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------
;;; Check if a move is valid
(define valid-move?
  (λ (brd blk-name delta-row delta-col)
    (let ([blk (hash-ref (board-blocks brd) blk-name)])
      (and
       (>= (block-on-board-row blk) 0)
       (<= (block-on-board-row blk) 
           (- (board-height brd) (if (is-vertical? blk) (block-length blk) 1)))
       (>= (block-on-board-column blk) 0)
       (<= (block-on-board-column blk)
           (- (board-width brd) (if (is-horizontal? blk) (block-length blk) 1)))
       (let* ([blk-o (block-orientation blk)]
              [blk-length (block-length blk)]
              [blk-row (block-on-board-row blk)]
              [blk-col (block-on-board-column blk)]
              [extended-blk
               (cond 
                 ((> delta-row 0)
                  (block-on-board blk-name blk-o (+ blk-length delta-row) blk-row blk-col))
                 ((< delta-row 0)
                  (block-on-board blk-name blk-o (- blk-length delta-row) (+ blk-row delta-row) blk-col))
                 ((> delta-col 0)
                  (block-on-board blk-name blk-o (+ blk-length delta-col) blk-row blk-col))
                 ((< delta-col 0)
                  (block-on-board blk-name blk-o (- blk-length delta-col) blk-row (+ blk-col delta-col))))])
         (andmap (λ (other-blk) (blocks-ok? other-blk extended-blk))
                 (hash-values (board-blocks brd)))
         )))))
;;; ----------------------------------------------------------------------------
(check-expect
 (valid-move?
  (board 5 5 (hash "red" (block-on-board "red" VERTICAL   2 0 2)
                   "b01" (block-on-board "b01" HORIZONTAL 2 1 0)
                   )) "b01" 0 1)
 #f)
(check-expect
 (valid-move?
  (board 5 5 (hash "red" (block-on-board "red" VERTICAL   2 0 2)
                   "b01" (block-on-board "b01" HORIZONTAL 2 1 0)
                   "b02" (block-on-board "b02" HORIZONTAL 2 3 3)
                   "b03" (block-on-board "b03" VERTICAL   2 0 4)
                   ))
  "b03" 1 0)
 #t)
;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------

;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------
;;; Generate all possible moves
(define get-reachable-states
  (λ (brd)
    (set! cnt (add1 cnt))
    (apply append
           (hash-map
            (board-blocks brd)
            (λ (blk-name blk)
              (map
               (λ (delta) (cons (brd blk-name (car delta) (cdr delta))
                                `(,blk-name ,(car delta) ,(cdr delta))))
               (filter
                (λ (delta) (valid-move? brd blk-name (car delta) (cdr delta)))
                (if (= (block-orientation blk) HORIZONTAL)
                    (map (λ (dc) (cons 0 dc))
                         (append (range -1 (* -1 (add1 (block-on-board-column blk))) -1)
                                 (range 1 (- (add1 (board-width brd))
                                             (+ (block-length blk)
                                                (block-on-board-column blk)))))
                         )
                    (map (λ (dr) (cons dr 0))
                         (append (range -1 (* -1 (add1 (block-on-board-row blk))) -1)
                                 (range 1 (- (add1 (board-height brd))
                                             (+ (block-length blk) 
                                                (block-on-board-row blk)))))
                         )
                    )
                )
               )
              )
            )
           )
    )
  )
;;; ----------------------------------------------------------------------------
(check-expect
 (length
  (get-reachable-states
   (board 5 5 (hash "red" (block-on-board "red" HORIZONTAL 2 2 2)))))
 3)
(check-expect
 (length 
  (get-reachable-states
   (board 5 5 (hash "red" (block-on-board "red" VERTICAL 2 2 2)))))
 3)
(check-expect
 (length (get-reachable-states
          (board 6 7 (hash "red" (block-on-board "red" HORIZONTAL 3 3 3)
                           "b01" (block-on-board "b01" HORIZONTAL 3 1 1)
                           "b02" (block-on-board "b02" VERTICAL   2 1 6)
                           "b03" (block-on-board "b03" HORIZONTAL 2 2 2)
                           "b04" (block-on-board "b04" VERTICAL   2 4 1)
                           ))))
 17)
;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------

(define print-plan
  (λ (plan)
    (for ([i (range 1 (add1 (length plan)))] [m plan])
      (display (~a "  " i ". Move block '" (first m) "' " 
                   (second m) " rows, " (third m) " columns.\n")))))

(define check-plan
  (λ (brd plan)
    (is-final? (foldl (λ (m b) (apply b m)) brd plan))))

;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------

(define get-color-from-name
  (λ (name)
    (foldl (λ (c old) (if (equal? (substring name 0 (min (string-length name) (string-length c))) c) c old))
           "slategray"
           '("green" "red" "orange" "purple" "blue"))))

(define display-board
  (λ (brd)
    (begin
      (let* ([cell-size 50]
             [frame (new frame%
                         [label "Unblock"]
                         [width (+ (* (board-width brd) cell-size) 40)]
                         [height (+ (* (board-width brd) cell-size) 40)])]
             [canvas (new canvas%
                          [parent frame]
                          [paint-callback
                           (λ (canvas dc)
                             (send dc set-pen "black" 2 'solid)
                             (send dc set-brush "whitesmoke" 'solid)
                             (send dc draw-rectangle 20 20 
                                   (+ (* (board-width brd) cell-size))
                                   (+ (* (board-height brd) cell-size)))
                             (for ([blk (hash-values (board-blocks brd))])
                               (send dc set-pen "black" 1 'solid)
                               (send dc set-brush (get-color-from-name (block-name blk))
                                     'solid)
                               (send dc draw-rectangle
                                     (+ (* (block-on-board-column blk) cell-size) 20)
                                     (+ (* (block-on-board-row blk) cell-size) 20)
                                     (* (if (is-horizontal? blk)
                                            (block-length blk) 1) cell-size)
                                     (* (if (is-vertical? blk)
                                            (block-length blk) 1) cell-size)))
                             
                             )])])
        (send frame show #t))
      )))

(define display-plan
  (λ (brd plan #:speed [speed .4])
    (let* ([cell-size 50]
           [_frame (new frame% [label "Unblock"])]
           [_canvas (new canvas% [parent _frame])]
           [redraw-func
            (λ (b)
              (let ([redraw-proc
                     (λ (dc)
                       (begin
                         (send dc erase)
                         (send dc set-brush "whitesmoke" 'solid)
                         (send dc set-pen "black" 2 'solid)
                         (send dc draw-rectangle 20 20 
                               (+ (* (board-width b) cell-size))
                               (+ (* (board-height b) cell-size)))
                         (for ([blk (hash-values (board-blocks b))])
                           (send dc set-pen "black" 1 'solid)
                           (send dc set-brush (get-color-from-name (block-name blk))
                                 'solid)
                           (send dc draw-rectangle
                                 (+ (* (block-on-board-column blk) cell-size) 20)
                                 (+ (* (block-on-board-row blk) cell-size) 20)
                                 (* (if (is-horizontal? blk)
                                        (block-length blk) 1) cell-size)
                                 (* (if (is-vertical? blk)
                                        (block-length blk) 1) cell-size)))
                         ))])
                
                (send _canvas refresh-now redraw-proc #:flush? #t)
                ))])
      (begin
        (send _frame resize
              (+ (* (board-width brd) cell-size) 40)
              (+ (* (board-width brd) cell-size) 40))
        (send _frame show #t)
        (sleep/yield speed)
        (redraw-func brd)
        (sleep/yield speed)
        (foldl (λ (m b)
                 (let ([b2 (apply b m)]) (redraw-func b2) (sleep/yield speed) b2))
               brd plan)
        (display "Done\n")
        )
      )))
            

;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------

(define board0a
  (board 5 5 (hash "red" (block-on-board "red" HORIZONTAL 2 1 2))))

(define board0b
  (board 4 4 (hash "red" (block-on-board "red" HORIZONTAL 2 1 0)
                   "blue1" (block-on-board "blue1" VERTICAL 2 0 2)
                   "blue2" (block-on-board "blue2" VERTICAL 2 1 3)
              )))

(define board0c
  (board 4 4 (hash "red" (block-on-board "red" HORIZONTAL 2 1 0)
                   "blue1" (block-on-board "blue1" VERTICAL 2 2 2)
                   "blue2" (block-on-board "blue2" VERTICAL 2 1 3)
              )))


(define board1 ;; unblock - level 1
  (board 6 6 (hash "blue1" (block-on-board "blue1" VERTICAL 2 0 0)
                   "blue2" (block-on-board "blue2" VERTICAL 2 0 1)
                   "red" (block-on-board "red" HORIZONTAL 2 2 0)
                   "purple" (block-on-board "purple" HORIZONTAL 2 4 0)
                   "orange1" (block-on-board "orange1" VERTICAL 3 0 3)
                   "orange2" (block-on-board "orange2" VERTICAL 3 0 4)
                   "orange3" (block-on-board "orange3" VERTICAL 3 0 5)
                   "green" (block-on-board "green" HORIZONTAL 3 3 3))
         ))

(define board2 ;; unblock - level 2
  (board 6 6 (hash "purple1" (block-on-board "purple1" HORIZONTAL 2 0 0)
                   "purple2" (block-on-board "purple2" HORIZONTAL 2 4 4)
                   "blue" (block-on-board "blue" VERTICAL 2 4 0)
                   "red" (block-on-board "red" HORIZONTAL 2 2 1)
                   "green" (block-on-board "green" HORIZONTAL 3 5 2)
                   "orange1" (block-on-board "orange1" VERTICAL 3 1 0)
                   "orange2" (block-on-board "orange2" VERTICAL 3 1 3)
                   "orange3" (block-on-board "orange3" VERTICAL 3 0 5))
         ))

(define board3 ;; unblock - level 3
  (board 6 6 (hash "purple1" (block-on-board "purple1" HORIZONTAL 2 0 3)
                   "purple2" (block-on-board "purple2" HORIZONTAL 2 1 2)
                   "purple3" (block-on-board "purple3" HORIZONTAL 2 5 4)
                   "blue1" (block-on-board "blue1" VERTICAL 2 3 1)
                   "blue2" (block-on-board "blue2" VERTICAL 2 3 3)
                   "blue3" (block-on-board "blue3" VERTICAL 2 3 4)
                   "blue4" (block-on-board "blue4" VERTICAL 2 1 4)
                   "blue5" (block-on-board "blue5" VERTICAL 2 0 5)
                   "red" (block-on-board "red" HORIZONTAL 2 2 2)
                   "green" (block-on-board "green" HORIZONTAL 3 5 1)
                   "orange1" (block-on-board "orange1" VERTICAL 3 3 0)
                   "orange2" (block-on-board "orange2" VERTICAL 3 0 1)
                   "orange3" (block-on-board "orange3" VERTICAL 3 2 5))
         ))

(define board4 ;; unblock - level 4
  (board 6 6 (hash "blue1" (block-on-board "blue1" VERTICAL 2 0 0)
                   "blue2" (block-on-board "blue2" VERTICAL 2 4 2)
                   "blue3" (block-on-board "blue3" VERTICAL 2 1 3)
                   "blue4" (block-on-board "blue4" VERTICAL 2 2 4)
                   "orange" (block-on-board "orange" VERTICAL 3 1 5)
                   "green1" (block-on-board "green1" HORIZONTAL 3 0 3)
                   "green2" (block-on-board "green2" HORIZONTAL 3 3 0)
                   "red" (block-on-board "red" HORIZONTAL 2 2 0)
                   "purple1" (block-on-board "purple1" HORIZONTAL 2 5 0)
                   "purple2" (block-on-board "purple2" HORIZONTAL 2 5 3)
                   "purple3" (block-on-board "purple3" HORIZONTAL 2 4 4))
         ))
(define board5 ;; unblock - level 5
  (board 6 6 (hash "blue1" (block-on-board "blue1" VERTICAL 2 2 2)
                   "blue2" (block-on-board "blue2" VERTICAL 2 4 2)
                   "blue3" (block-on-board "blue3" VERTICAL 2 1 4)
                   "orange1" (block-on-board "orange1" VERTICAL 3 0 5)
                   "orange2" (block-on-board "orange2" VERTICAL 3 2 3)
                   "orange3" (block-on-board "orange3" VERTICAL 3 3 1)
                   "green1" (block-on-board "green1" HORIZONTAL 3 1 0)
                   "red" (block-on-board "red" HORIZONTAL 2 2 0)
                   "purple1" (block-on-board "purple1" HORIZONTAL 2 0 0)
                   "purple2" (block-on-board "purple2" HORIZONTAL 2 0 2))
         ))

(define board6 ;; unblock2 - expert - level 1
  (board 6 6 (hash "blue1" (block-on-board "blue1" VERTICAL 2 3 0)
                   "blue2" (block-on-board "blue2" VERTICAL 2 0 1)
                   "blue3" (block-on-board "blue3" VERTICAL 2 3 3)
                   "blue4" (block-on-board "blue4" VERTICAL 2 3 4)
                   "blue5" (block-on-board "blue5" VERTICAL 2 2 5)
                   "blue6" (block-on-board "blue6" VERTICAL 2 4 5)
                   "orange1" (block-on-board "orange1" VERTICAL 3 1 2)
                   "green1" (block-on-board "green1" HORIZONTAL 3 1 3)
                   "green2" (block-on-board "green2" HORIZONTAL 3 5 0)
                   "red" (block-on-board "red" HORIZONTAL 2 2 0)
                   "purple1" (block-on-board "purple1" HORIZONTAL 2 0 2)
                   "purple2" (block-on-board "purple2" HORIZONTAL 2 0 4)
                   "purple3" (block-on-board "purple3" HORIZONTAL 2 4 1))
         ))

(define board7 ;; unblock2 - expert - level 2
  (board 6 6 (hash "blue1" (block-on-board "blue1" VERTICAL 2 4 2)
                   "blue2" (block-on-board "blue2" VERTICAL 2 1 3)
                   "blue3" (block-on-board "blue3" VERTICAL 2 3 3)
                   "orange1" (block-on-board "orange1" VERTICAL 3 0 0)
                   "orange2" (block-on-board "orange2" VERTICAL 3 1 5)
                   "green1" (block-on-board "green1" HORIZONTAL 3 3 0)
                   "green2" (block-on-board "green2" HORIZONTAL 3 0 3)
                   "red" (block-on-board "red" HORIZONTAL 2 2 1)
                   "purple1" (block-on-board "purple1" HORIZONTAL 2 5 3))
         ))

(define board8 ;; unblock2 - expert - level 3
  (board 6 6 (hash "blue1" (block-on-board "blue1" VERTICAL 2 3 0)
                   "blue2" (block-on-board "blue2" VERTICAL 2 4 2)
                   "blue3" (block-on-board "blue3" VERTICAL 2 0 4)
                   "blue4" (block-on-board "blue4" VERTICAL 2 2 4)
                   "orange1" (block-on-board "orange1" VERTICAL 3 0 3)
                   "green1" (block-on-board "green1" HORIZONTAL 3 1 0)
                   "red" (block-on-board "red" HORIZONTAL 2 2 0)
                   "purple1" (block-on-board "purple1" HORIZONTAL 2 3 2)
                   "purple2" (block-on-board "purple2" HORIZONTAL 2 4 3)
                   "purple3" (block-on-board "purple3" HORIZONTAL 2 5 0))
         ))

(define board9 ;; unblock2 - expert - level 4
  (board 6 6 (hash "blue1" (block-on-board "blue1" VERTICAL 2 0 0)
                   "blue2" (block-on-board "blue2" VERTICAL 2 0 4)
                   "blue3" (block-on-board "blue3" VERTICAL 2 4 4)
                   "orange1" (block-on-board "orange1" VERTICAL 3 1 2)
                   "orange2" (block-on-board "orange2" VERTICAL 3 1 3)
                   "orange3" (block-on-board "orange3" VERTICAL 3 0 5)
                   "red" (block-on-board "red" HORIZONTAL 2 2 0)
                   "purple1" (block-on-board "purple1" HORIZONTAL 2 0 1)
                   "purple2" (block-on-board "purple2" HORIZONTAL 2 3 4)
                   "purple3" (block-on-board "purple3" HORIZONTAL 2 5 1))
         ))

(define board10 ;; unblock2 - expert - level 5
  (board 6 6 (hash "blue1" (block-on-board "blue1" VERTICAL 2 3 0)
                   "blue2" (block-on-board "blue2" VERTICAL 2 4 2)
                   "blue3" (block-on-board "blue3" VERTICAL 2 0 4)
                   "blue4" (block-on-board "blue4" VERTICAL 2 2 4)
                   "blue5" (block-on-board "blue5" VERTICAL 2 1 5)
                   "blue6" (block-on-board "blue6" VERTICAL 2 3 5)
                   "orange1" (block-on-board "orange1" VERTICAL 3 0 3)
                   "green1" (block-on-board "green1" HORIZONTAL 3 1 0)
                   "red" (block-on-board "red" HORIZONTAL 2 2 0)
                   "purple1" (block-on-board "purple1" HORIZONTAL 2 0 0)
                   "purple2" (block-on-board "purple2" HORIZONTAL 2 3 2)
                   "purple3" (block-on-board "purple3" HORIZONTAL 2 4 3)
                   "purple4" (block-on-board "purple4" HORIZONTAL 2 5 0))
         ))

(define board11 ;; original
  (board 10 10 (hash "orange0" (block-on-board "orange0" VERTICAL 3 1 0)
                     "orange1" (block-on-board "orange1" VERTICAL 3 7 1)
                     "orange2" (block-on-board "orange2" VERTICAL 3 3 2)
                     "orange3" (block-on-board "orange3" VERTICAL 3 5 3)
                     "orange4" (block-on-board "orange4" VERTICAL 3 7 4)
                     "orange5" (block-on-board "orange5" VERTICAL 3 3 5)
                     "orange6" (block-on-board "orange6" VERTICAL 3 7 6)
                     "orange7" (block-on-board "orange7" VERTICAL 3 5 7)
                     "orange8" (block-on-board "orange8" VERTICAL 3 3 8)
                     "orange9" (block-on-board "orange9" VERTICAL 3 4 9)
                     "red" (block-on-board "red" HORIZONTAL 2 0 5)
                     "purple0" (block-on-board "purple0" HORIZONTAL 2 1 2)
                     "purple1" (block-on-board "purple1" HORIZONTAL 2 8 2)
                     "purple2" (block-on-board "purple2" HORIZONTAL 2 9 2)
                     "purple3" (block-on-board "purple3" HORIZONTAL 2 0 5)
                     "purple4" (block-on-board "purple4" HORIZONTAL 2 6 5)
                     "purple5" (block-on-board "purple5" HORIZONTAL 2 3 6)
                     "purple6" (block-on-board "purple6" HORIZONTAL 2 2 8)
                     "purple7" (block-on-board "purple7" HORIZONTAL 2 7 8)
                     )
         ))

;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------
;;; Implemented DFS and BFS search

(define extract-plan
  (λ (all-states current-state plan)
    (let* (;; get info about how current state was reached : (prev-state . move)
           [prev-info (hash-ref all-states current-state)]
           [prev-state (car prev-info)]
           [move (cdr prev-info)])
      (if (null? prev-state) ;; if current state was the start state
          plan               ;; return the plan
          ;; else add the move to the plan and recover the rest of the path
          (extract-plan all-states prev-state (cons move plan)))
      )
    ))

(define search-dfs-plan
  (λ (frontier closed)
    (if (null? frontier)
        (error "Solution cannot be reached.")
        (let ([current-state (caar frontier)]
              [prev-info (cdar frontier)])
          (cond
            ((is-final? current-state)
             (extract-plan (hash-set closed current-state prev-info) current-state '()))
            ((hash-has-key? closed current-state)
             (search-dfs-plan (cdr frontier) closed))
            (else
             (search-dfs-plan
              (append (map (λ (info)
                             (cons (car info) (cons current-state (cdr info))))
                           (get-reachable-states current-state))
                      (cdr frontier))
              (hash-set closed current-state prev-info)
              )
             ))
          ))
    ))

(define search-bfs-plan
  (λ (frontier closed)
    (if (null? frontier)
        (error "Solution cannot be reached.")
        (let ([current-state (caar frontier)]
              [prev-info (cdar frontier)])
          (cond
            ((is-final? current-state)
             (extract-plan (hash-set closed current-state prev-info) current-state '()))
            ((hash-has-key? closed current-state)
             (search-bfs-plan (cdr frontier) closed))
            (else
             (search-bfs-plan
              (append (cdr frontier)
                      (map (λ (info)
                             (cons (car info) (cons current-state (cdr info))))
                           (get-reachable-states current-state)))
              (hash-set closed current-state prev-info)
              )
             ))
          ))
    ))

(define dfs (λ (brd) (search-dfs-plan `((,brd . (()))) (hash))))
(define bfs (λ (brd) (search-bfs-plan `((,brd . (()))) (hash))))
(test)

;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------

(define member?
  (λ (x L)
    (cond
      [(empty? L) #f]
      [(equal? x (car L)) #t]
      [else (member? (cdr L))]
      )))

;;sorteaza elementele din frontiera in functie de g , astfel daca vom avea 2 elemente identice ,
;;primul selectat va fi cel cu g-ul mai mic


(define sort-frontier (λ (frontier) (sort frontier (λ (x y) (< (car x) (car y)))))) 

;;functie ajutatoare pentru copy-value?
(define copy-valueh?
  (λ (x frontier)
    (cond
      [(empty? frontier) 1000]
      [(and (equal? (cadar frontier) (car x)) (equal? (cddar frontier) (cdr x))) (caar frontier)]
      [else (copy-valueh? x (cdr frontier))]
    )))

;va intoarce 
(define copy-value?
  (λ (x frontier)
    (copy-valueh? x (sort-frontier frontier))
    ))

(define copy-succesorh?
  (λ (x frontier)
    (cond
      [(empty? frontier) '()]
      [(and (equal? (cadar frontier) (car x)) (equal? (cddar frontier) (cdr x))) (car frontier)]
      [else (copy-succesorh? x (cdr frontier))]
    )))

(define copy-succesor?
  (λ (x frontier)
        (copy-succesorh? x (sort-frontier frontier))
    ))

;functia intoarce numarul de blocuri ce blocheaza calea blocului rosu
(define obstacle-blocks
  (λ (board)
    (let* ([hero-block (hash-ref (board-blocks board) "red")] ;blocul rosu
           [width (board-width board)]
           [height (board-height board)]
           [column (block-on-board-column hero-block)]
           [row (block-on-board-row hero-block)]
           [length (block-length hero-block)])
      (remove hero-block (filter (λ (x) 
                                 (cond
                                   [(is-horizontal? x) #f]
                                   [(> (block-on-board-row x) row) #f]
                                   [(< (block-on-board-column x) (+ column length)) #f]
                                   [(and (= (block-on-board-row x) row) ) #t]
                                   [(and (> (+ (block-on-board-row x) (block-length x)) row) (< (block-on-board-row x) row)) #t]
                                   [else #f]
                                   ))
                                 (hash-values (board-blocks board)))))))


;functia h de evaluare(euristica)
;va intoarce numarul de obstacole
(define h-eval
  (λ (board)
    (length (obstacle-blocks board))
    ))

;va primi ca parametru un element din frontiera
;structura unui element din frontiera va fi o lista de forma (g current-state prev-info)
;va intoarce f = g + h
(define board-eval
  (λ (frontier-element)
    (let* ([g (car frontier-element)]
          [h (h-eval (cadr frontier-element))]
          [w (+ g h)])
      w
    )))

;va intoarce elementul cu f-ul minim din frontiera
;f = g+h
(define min-frontier
  (λ (frontier)
    (argmin board-eval frontier)))

;verifica daca un succesor se afla deja in frontiera
(define frontier-member?
  (λ (x frontier)
    (cond
      [(empty? frontier) #f]
      [(and (equal? (cadar frontier) (car x)) (equal? (cddar frontier) (cdr x))) #t]
      [else (frontier-member? x (cdr frontier))]
    )))

;; implementați aici algoritmul A*

;functia aplica partea din algoritmul A* destinata succesorului
;daca succesorul are deja o copie existenta in frontiera sau teritoriu si are g-ul mai mare decat al acesteia , atunci nu il mai adaug in frontiera
;altfel , il voi adauga in frontiera
;g-ul succesorilor va fi g+1
(define succesor
  (λ (reachable-states current-state prev-info current-element g frontier closed)
    (if (empty? reachable-states)
        frontier      
    (let* ([info (car reachable-states)]
           [new-element (cons (+ g 1) (cons (car info) (cons current-state (cdr info))))])
        (if ( and (frontier-member? (cons (car info) current-state) frontier) (> (+ g 1) (copy-value? (cons (car info) current-state) frontier)))
            (succesor (cdr reachable-states) current-state prev-info current-element g frontier closed) ;continue
            (if (and (hash-has-key? closed (car info))  (> (+ g 1) (copy-value? (cons (car info) current-state) frontier)))
               (succesor (cdr reachable-states) current-state prev-info current-element g frontier closed) ;continue             
               (succesor (cdr reachable-states) current-state prev-info current-element g (cons new-element frontier) closed) ; adaug noul element in frontiera
               ;(if (and(< (+ g 1) (copy-value? (cons (car info) (cadr info)) frontier)) (> (+ g 1) (copy-value? (cons (car info) (cadr info)) frontier)))
            ;(succesor (cdr reachable-states) current-state prev-info current-element g (cons new-element (remove (copy-succesor? (cons (car info) (cadr info)) frontier ) frontier)) closed) ; adaug noul element in frontiera
            ;(succesor (cdr reachable-states) current-state prev-info current-element g frontier closed)
                  ;  (if ( not (frontier-member? (cons (car info) (cdr info)) frontier))
            ;(succesor (cdr reachable-states) current-state prev-info current-element g (cons new-element frontier) closed) ; adaug noul element in frontiera
            ;(if (< (+ g 1) (copy-value? (cons (car info) (cadr info)) frontier))
            ;(succesor (cdr reachable-states) current-state prev-info current-element g (cons new-element (remove (copy-succesor? (cons (car info) (cadr info)) frontier ) frontier)) closed) ; adaug noul element in frontiera
            ;(succesor (cdr reachable-states) current-state prev-info current-element g frontier closed)
         
      ;[(not (or (frontier-member? (cons (car info) (cdr info)) frontier) (hash-has-key? closed (car info))))
       ; (succesor (cdr reachable-states) current-state prev-info current-element g (cons new-element frontier) closed)]
      ;[(< (+ g 1) (copy-value? (cons (car info) (cadr info)) (sort frontier (λ (x y) (< (car x) (car y)))))) 
      ;(succesor (cdr reachable-states) current-state prev-info current-element g (cons new-element (remove (copy-succesor?  (sort frontier (λ (x y) (< (car x) (car y))))) frontier closed)))] ;;as putea sa folosesc cons in loc de append pt a fi mai rapid
      ;[else (succesor (cdr reachable-states) current-state prev-info current-element g frontier closed)]
    ))))))


;implementarea algoritmului A-star
(define A-star
  (λ (frontier closed)
    (if (null? frontier)
        (error "Solutin cannot be reached.")
         (let* ([current-element (min-frontier frontier)] ;nodul cu w minim din frontiera
               [current-state (cadr current-element)]
               [prev-info (cddr current-element)]
               [g (car current-element)])
           (cond
            [(is-final? current-state)
             (extract-plan (hash-set closed current-state prev-info) current-state '())]
            [(hash-has-key? closed current-state ) 
             (A-star (remove current-element frontier) closed)]
            [else (A-star
                   (succesor (get-reachable-states current-state) current-state prev-info current-element g (remove current-element frontier) closed) ;;generez succesorii
                   (hash-set closed current-state prev-info)) ;adaug nodul in teritoriu 
            ]          
    )))))

(define a-star (λ (brd) (A-star `( (0 ,brd . (()))) (hash))))






;;;---------------------------------------

;;VERIFICA DACA BLOCURILE SE AFLA PE ACEASI COLOANA
;;SAU DACA UNUL TRECE PRIN COLOANA CELUILALT
(define same-column?
  (λ (x column)
    (if (= (block-on-board-column x) column)
        #t
        (if (is-horizontal? x)
            (if (and (< (block-on-board-column x) column) (> (+ (block-on-board-column x) (block-length x)) column))
            #t
            #f) 
            #f
    )
  )
    )
  )

;FUNCTII AJUTATOARE PT EURISTICA
(define new-eval
  (λ (L)
    (if (empty? L)
        1
        2
        )
    ))

(define is-blocking-up
  (λ (x row)
    (if (= (block-on-board-row x) (- row 1))
        #t
        #f
        )
        )
  )

(define is-blocking-down
  (λ (x row length)
    (if (= (block-on-board-row x) (+ row length ))
        #t
        #f
        )
        )
  )

(define above
  (λ (L row)
    (filter (λ (x) (is-blocking-up x row)) L)
    ))

(define below
  (λ (L row length)
    (filter (λ (x) (is-blocking-down x row length)) L)
    ))

;column = coloana blocului obstacol
;row = linia blocului obstacol
;length = lungimea blocului obstacol
;heigth = lungimea tablei
(define column-blocks2
  (λ (block-list column row length red-row b-height)
      (min (new-eval (above (filter (λ (x) (and (< (block-on-board-row x) row) (same-column? x column))) block-list) row )) 
           (new-eval (below (filter (λ (x) (and (> (block-on-board-row x) (+ row (block-length x))) (same-column? x column))) block-list) row length) )
           )))


;;Noua euristica
;obstacles-blocks-board
;h-eval
;block-list contine lista tuturor blocurilor din board
;obstacle-list contine lista blocurilor care se afla pe traiectoria lui "red"
(define new-h
  (λ (block-list obstacle-list red-row b-height)
    (apply + (map (λ (x) (column-blocks2 block-list (block-on-board-column x) (block-on-board-row x) (block-length x) red-row b-height)) obstacle-list))
    ))

(define board-eval2
  (λ (frontier-element)
    (let* (;[board (cadr frontier-element)]
           [height (board-height (cadr frontier-element))]
           [red-row (block-on-board-row (hash-ref (board-blocks (cadr frontier-element)) "red"))]
           [g (car frontier-element)]
           [h (new-h  (hash-values (board-blocks (cadr frontier-element))) (obstacle-blocks (cadr frontier-element)) red-row height)]
           [w (+ g h)])
      w
    )))


(define min-frontier2
  (λ (frontier)
    (argmin board-eval2 frontier)))

;Implementare folosind a 2-a euristica
;e practic copy-paste de la primul , doar ca folosesc alta euristica la evaluare


;implementarea algoritmului A-star
(define A-star2
  (λ (frontier closed)
    (if (null? frontier)
        (error "Solutin cannot be reached.")
         (let* ([current-element (min-frontier2 frontier)] ;nodul cu w minim din frontiera
               [current-state (cadr current-element)]
               [prev-info (cddr current-element)]
               [g (car current-element)])
           (cond
            [(is-final? current-state)
             (extract-plan (hash-set closed current-state prev-info) current-state '())]
            [(hash-has-key? closed current-state ) 
             (A-star (remove current-element frontier) closed)]
            [else (A-star
                   (succesor (get-reachable-states current-state) current-state prev-info current-element g (remove current-element frontier) closed) ;;generez succesorii
                   (hash-set closed current-state prev-info)) ;adaug nodul in teritoriu 
            ]          
    )))))

(define a-star2 (λ (brd) (A-star2 `( (0 ,brd . (()))) (hash))))


;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------
;;; ----------------------------------------------------------------------------

(define compare-algorithms
  (λ (algorithms names boards)
    (when (not (= (length algorithms) (length names)))
      (error "Each algorithm should have a name"))
    (let* ([col-widths (map (λ (n) (max 10 (+ 2 (string-length n))))
                            (cons "board" names))]
           [line-builder (λ (words sep align fill)
                           (apply string-append (append `(,sep)
                                  (map (λ (wo wi) 
                                         (string-append
                                          (~a wo #:min-width wi #:align align
                                              #:right-pad-string fill
                                              #:left-pad-string fill)
                                          sep)) words col-widths)
                                  '("\n"))))]
           [separator (line-builder (map (λ (x) "-") (cons "board" names))
                                    "+" 'right "-")]
           [header (line-builder (cons "board" names) "|" 'center " ")]
           [score-line (λ (scores) (line-builder scores "|" 'right " "))]
           [display-header (λ () (begin (display separator) (display header)
                                        (display separator)))]
           [display-scores (λ (scores) (begin (display (score-line scores))
                                              (display separator)))])
      (begin
        (display-header)
        (for ([b boards] [i (range 1 (add1 (length boards)))])
          (display-scores
           (cons (number->string i)
                 (map (λ (a) (begin (set! cnt 0)
                                    (if (check-plan b (a b)) cnt "GREȘIT")))
                      algorithms))))
        ))))



;;; ----------------------------------------------------------------------------
;;; Comparați algoritmii pe diferite scenarii
;(display-board board7)
;(display-plan board4 (dfs board4) #:speed .2)
(compare-algorithms `(,dfs ,bfs ,a-star)
                    `("DFS" "BFS" "A*")
                    `(,board1 ,board2 ,board3 ,board4 ,board5 
                              ,board6 ,board7 ,board8 ,board9 ,board10))


;;;; !!!!!!!! ATENTIE 
;Pentru a rula algoritmul a star-2 decomentati

;(compare-algorithms `(,dfs ,bfs ,a-star2)
 ;                   `("DFS" "BFS" "A*")
  ;                  `(,board1 ,board2 ,board3 ,board4 ,board5 
   ;                           ,board6 ,board7 ,board8 ,board9 ,board10))

;;; ----------------------------------------------------------------------------

