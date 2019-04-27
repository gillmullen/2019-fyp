declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.p.t = alloca i1
store i1 true, i1* %.p.t
%.t1 = load i1, i1* %.p.t
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i1 0, i1 0), i1 %.t1)
ret i32 0
}
