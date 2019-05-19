declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.p.goals = alloca i32
%.p.points = alloca i32
store i32 2, i32* %.p.goals
store i32 10, i32* %.p.points
%.t2 = load i32, i32* %.p.goals
%.t1 = mul i32 %.t2, 3
%.t4 = load i32, i32* %.p.points
%.t3 = add i32 %.t1, %.t4
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t3)
ret i32 0
}
