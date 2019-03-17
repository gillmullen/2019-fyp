declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.t1 = getelementptr [12 x i8], [12 x i8]* @., i64 0, i64 0
call i32 @puts (i8* %.t1)
ret i32 0
}
