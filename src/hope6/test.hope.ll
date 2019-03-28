declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.p.arr = alloca [3 x i8*]
store [null x i32] null, [null x i32]* %.p.arr
%.t1 = load [null x i32], [null x i32]* %.p.arr
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, [null x i32] 0, [null x i32] 0), [null x i32] %.t1)
ret i32 0
}
