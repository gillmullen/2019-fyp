declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.p.arr = alloca [3 x i32]
store [3 x i32] [i32 1, i32 2, i32 3], [3 x i32]* %.p.arr
%.t1 = load [3 x i32], [3 x i32]* %.p.arr
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, [3 x i32] 0, [3 x i32] 0), [3 x i32] %.t1)
ret i32 0
}
