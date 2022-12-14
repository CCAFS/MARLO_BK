INSERT INTO parameters (global_unit_type_id, `key`, description, format, category)
VALUES (1, 'crp_email_pl_crpAdmin_fl', 'Send CC email to PL, CRP Admin and FL Only',1,2);
INSERT INTO parameters (global_unit_type_id, `key`, description, format, category)
VALUES (3, 'crp_email_pl_crpAdmin_fl', 'Send CC email to PL, CRP Admin and FL Only',1,2);
INSERT INTO parameters (global_unit_type_id, `key`, description, format, category)
VALUES (4, 'crp_email_pl_crpAdmin_fl', 'Send CC email to PL, CRP Admin and FL Only',1,2);

INSERT INTO custom_parameters(parameter_id, `value`, created_by, is_active, active_since, modified_by, modification_justification, global_unit_id)
SELECT
p.id, 'true', 3, 1, now(), 3, '', g.id
FROM
global_units AS g
INNER JOIN global_unit_types AS gt ON g.global_unit_type_id = gt.id
INNER JOIN parameters AS p ON p.global_unit_type_id = gt.id
WHERE
p.`key` = 'crp_email_pl_crpAdmin_fl'
AND
p.global_unit_type_id=1;