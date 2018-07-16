module CatenableList (CatenableList(..)) where
    import Prelude hiding (head, tail, (++))
    import Queue

-- The value can be any type for example string or integer
class CatenableList catenableList where
    empty :: catenableList value
    isEmpty :: catenableList value -> Bool
    -- Takes a value and a catenable list as arguments
    -- Returns a new catenable list with the value as the first element
    cons :: value -> catenableList value -> catenableList value
    snoc :: catenableList value -> value -> catenableList value
    -- Takes two catenable lists as arguments
    -- Returns the concatenation as a new catenable list 
    (++) :: catenableList value -> catenableList value -> catenableList value
    head :: catenableList value -> value
    tail :: catenableList value -> catenableList value
        